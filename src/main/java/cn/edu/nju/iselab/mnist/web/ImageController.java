package cn.edu.nju.iselab.mnist.web;

import cn.edu.nju.iselab.mnist.dt.ImageDto;
import cn.edu.nju.iselab.mnist.dt.ImageRepository;
import cn.edu.nju.iselab.mnist.io.ImageWriter;
import cn.edu.nju.iselab.mnist.vo.Base64ImageVo;
import cn.edu.nju.iselab.mnist.vo.ImageRecognizeVo;
import cn.edu.nju.iselab.mnist.vo.ResponseVo;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ImageController {

    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);
    private @Autowired
    ImageRepository imageRepository;

    private @Autowired
    ImageWriter handwriteImageWriter;

    @PostMapping("/handwrite")
    public @ResponseBody
    ResponseVo<Object> uploadBase64Image(@RequestBody Base64ImageVo imgInfo) {
        LOG.info("Image data: " + imgInfo.getBase64Encoded());
        String imgPath = handwriteImageWriter.write(imgInfo.getBase64Encoded().split(",")[1].trim());
        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            return ResponseVo.ok(imgFile.getName());
        } else {
            return ResponseVo.failed("We received the buffered data, but we can't handle this data, please ensure it is a legal buffered data, and you can try it after a moment.");
        }
    }

    @PostMapping("recognize")
    public @ResponseBody ResponseVo<Object> recognizeImage(@RequestBody Base64ImageVo imgInfo) {
        LOG.info("Image Name: " + imgInfo.getImageName());
        try {
            BufferedImage img = ImageIO.read(new File("handwrite", imgInfo.getImageName()));
            Image scaledInstance = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            BufferedImage scaledImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = scaledImage.getGraphics();
            graphics.drawImage(scaledInstance, 0, 0, null);
            graphics.dispose();

            File scaledImageFile = new File("handwrite", new Date().getTime() + "");
            scaledImageFile.mkdir();
            File dest = new File(scaledImageFile, imgInfo.getImageName() + "x28.png");
            ImageIO.write(scaledImage, "png", dest);

            ImageRecordReader imageRecordReader = new ImageRecordReader(28, 28, 1);
            FileSplit imgSplit = new FileSplit(scaledImageFile, NativeImageLoader.ALLOWED_FORMATS);

            imageRecordReader.initialize(imgSplit);
            DataSetIterator imgDsIt = new RecordReaderDataSetIterator(imageRecordReader, 1);
            ImagePreProcessingScaler preprocessing = new ImagePreProcessingScaler(0, 1);
            preprocessing.fit(imgDsIt);
            MultiLayerNetwork cnn = ModelSerializer.restoreMultiLayerNetwork(getClass().getResourceAsStream("/models/mnist-cnn.mdl"), true);

            DataSet ds = imgDsIt.next();
            int[] predict = cnn.predict(ds.getFeatures());
            LOG.info(Arrays.toString(cnn.output(ds.getFeatures()).toDoubleVector()));
            ImageDto imageDto = new ImageDto();
            imageDto.setName(imgInfo.getImageName() + "x28.png");
            imageDto.setPath(dest.getAbsolutePath());
            imageDto.setLabel("" + predict[0]);
            imageDto.setTrained(false);
            imageRepository.save(imageDto);

            return ResponseVo.ok(predict);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVo.failed("We received your request, but we can't process it.");
        }
    }

    @PostMapping("/adjust")
    public @ResponseBody ResponseVo<Object> submitRecognizeResult(@RequestBody ImageRecognizeVo recResult) {
        ImageDto byName = imageRepository.findByName(recResult.getName() + "x28.png");
        byName.setLabel(recResult.getLabel());
        imageRepository.save(byName);
        return ResponseVo.ok(byName);
    }

}

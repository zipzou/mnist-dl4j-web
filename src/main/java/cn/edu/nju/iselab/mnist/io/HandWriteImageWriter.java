package cn.edu.nju.iselab.mnist.io;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Component
public class HandWriteImageWriter implements ImageWriter {
    /**
     * Store the image data encoded by Base64 encoding.
     *
     * @param base64Encoded the image buffer encoded by Base64
     * @return the file url in the disk
     */
    @Override
    public String write(String base64Encoded) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        OutputStream imgOutputStream = null;
        String imgUrl = null;
        try {
            byte[] imgBuffer = base64Decoder.decodeBuffer(base64Encoded);
            for (int i = 0; i < imgBuffer.length; i++) {
                if (0 > imgBuffer[i]) {
                    imgBuffer[i] += 256;
                }
            }
            File imgFolder = new File("handwrite");
            if (!imgFolder.exists()) {
                imgFolder.mkdir();
            }
            File imgFile = new File(imgFolder,  "" + new Date().getTime());
            imgOutputStream = new FileOutputStream(imgFile);
            IOUtils.write(imgBuffer, imgOutputStream);
            imgUrl = imgFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != imgOutputStream) {
                try {
                    imgOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgUrl;
    }
}

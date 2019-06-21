package cn.edu.nju.iselab.mnist.io;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingleImageReader implements ImageReader {
    /**
     * Read images from a folder.
     *
     * @param path the folder of images
     * @return images placed in the <pre>path</pre>
     */
    @Override
    public List<BufferedImage> read(String path) {
        File imageFolder = new File(path);
        Collection<File> imageFiles = FileUtils.listFiles(imageFolder, new String[]{"png", "jpg", "jpeg"}, false);
        List<BufferedImage> images = new ArrayList<BufferedImage>();
        for (File imageFile :
                imageFiles) {
            try {
                BufferedImage img = ImageIO.read(imageFile);
                images.add(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}

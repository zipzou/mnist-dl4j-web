package cn.edu.nju.iselab.mnist.io;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 */
public interface ImageReader {
    /**
     * Read images from a folder.
     * @param path the folder of images
     * @return images placed in the <pre>path</pre>
     */
    public List<BufferedImage> read(String path);


}

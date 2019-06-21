package cn.edu.nju.iselab.mnist.io;

public interface ImageWriter {

    /**
     * Store the image data encoded by Base64 encoding.
     * @param base64Encoded the image buffer encoded by Base64
     * @return the file url in the disk
     */
    public String write(String base64Encoded);
}

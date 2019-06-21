package cn.edu.nju.iselab.mnist.vo;

/**
 * The request value object passed by view.
 */
public class Base64ImageVo {
    private String imageName;
    private String base64Encoded;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBase64Encoded() {
        return base64Encoded;
    }

    public void setBase64Encoded(String base64Encoded) {
        this.base64Encoded = base64Encoded;
    }
}

package ro.sergiu.photogallery.api.models;

/**
 * Created by Sergiu on 18.03.2016.
 */
public class ImagePOST {
    String file;
    Integer user;

    public ImagePOST() {

    }

    public ImagePOST(String file, Integer user) {
        this.file = file;
        this.user = user;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}

package ro.sergiu.photogallery.api.models;

/**
 * Created by Sergiu on 18.03.2016.
 */
public class ImageGET {
    private Integer id;
    private String file;
    private String comment;
    private Integer user;

    public ImageGET(Integer id, String file, String comment, Integer user) {
        this.id = id;
        this.file = file;
        this.comment = comment;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}

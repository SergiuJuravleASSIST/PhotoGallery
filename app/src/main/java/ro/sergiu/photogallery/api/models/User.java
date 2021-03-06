package ro.sergiu.photogallery.api.models;

/**
 * Created by Sergiu on 18.03.2016.
 */
public class User {
    private String first_name;
    private String last_name;
    private String username;

    public User(String firstName, String lastName, String username) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

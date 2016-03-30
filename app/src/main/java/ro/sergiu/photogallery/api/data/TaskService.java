package ro.sergiu.photogallery.api.data;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;
import ro.sergiu.photogallery.api.models.ImageGET;
import ro.sergiu.photogallery.api.models.User;


/**
 * Created by Sergiu on 18.03.2016.
 */
public interface TaskService {
    @GET("/files/user/{id}/")
    void getImages(@Path("id") int userId, Callback<List<ImageGET>> callBack);

    @GET("/user/{id}/")
    void getUser(@Path("id") int userId, Callback<User> callback);

    @Multipart
    @POST("/files/upload/")
    void sendImage(@Part("file") TypedFile file,
                   @Part("user") int user,
                   Callback<ImageGET> callback);
}

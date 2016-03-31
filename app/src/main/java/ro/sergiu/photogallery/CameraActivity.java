package ro.sergiu.photogallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import ro.sergiu.photogallery.api.Tasks;
import ro.sergiu.photogallery.api.data.ServiceGenerator;
import ro.sergiu.photogallery.api.data.TaskService;
import ro.sergiu.photogallery.api.models.ImageGET;

public class CameraActivity extends AppCompatActivity {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Context mContext = this;
    private Uri fileUri;
    private Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = Utils.getOutputMediaFileUri(Utils.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tasks.ImageSendTask imageSendTask = new Tasks.ImageSendTask(mContext, fileUri);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(Utils.isOnline(mContext)) {
                    imageSendTask.execute();
                } else {
                    Toast.makeText(mContext, "Failed to send image..", Toast.LENGTH_LONG).show();
                }
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                Toast.makeText(this, "Image capture failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}

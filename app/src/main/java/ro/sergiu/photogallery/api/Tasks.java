package ro.sergiu.photogallery.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import ro.sergiu.photogallery.Utils;
import ro.sergiu.photogallery.api.data.ServiceGenerator;
import ro.sergiu.photogallery.api.data.TaskService;
import ro.sergiu.photogallery.api.models.ImageGET;

/**
 * Created by User on 29.03.2016.
 */
public class Tasks {

    public static void sendImage(final Context context, TypedFile typedFile, int user, final Uri fileUri) {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        taskService.sendImage(typedFile, user, new Callback<ImageGET>() {
            @Override
            public void success(ImageGET s, Response response) {
                if (response != null) {
                    Toast.makeText(context, "Gallery updated.", Toast.LENGTH_LONG).show();
                    Utils.deleteFile(fileUri);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "Failed to send image..", Toast.LENGTH_LONG).show();
                Log.e("Error", error.toString());
            }
        });
    }

    public static  class ImageSendTask extends AsyncTask<Void, Void, Bitmap> {
        Context mContext;
        Uri mUri;

        public ImageSendTask(Context mContext, Uri uri) {
            this.mContext = mContext;
            mUri = uri;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return Glide
                        .with(mContext)
                        .load(mUri)
                        .asBitmap()
                        .into(250, 250)
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmap) {
            if (bmap != null) {
                try {
                    File f = new File(mContext.getCacheDir(), "image");

                    FileOutputStream fileOutputStream = new FileOutputStream(f);
                    fileOutputStream.write(compressBitmap(bmap));
                    fileOutputStream.close();

                    TypedFile typedFile = new TypedFile("multipart/form-data", f);

                    sendImage(mContext, typedFile, 1, mUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static byte[] compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

        return baos.toByteArray();
    }
}

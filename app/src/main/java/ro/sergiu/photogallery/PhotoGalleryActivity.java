package ro.sergiu.photogallery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ro.sergiu.photogallery.Service.MyReceiver;
import ro.sergiu.photogallery.api.data.ServiceGenerator;
import ro.sergiu.photogallery.api.data.TaskService;
import ro.sergiu.photogallery.api.models.ImageGET;
import ro.sergiu.photogallery.ui.ImageListAdapter;

public class PhotoGalleryActivity extends AppCompatActivity {
    private List<String> imagesUrls = new ArrayList<>();
    private Context mContext = this;
    private GridView gridView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        gridView = (GridView) findViewById(R.id.gridView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        getImageList(1);

        if (gridView != null) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Uri photoUri = Uri.parse(imagesUrls.get(position));
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra("URI", photoUri.toString());
                    startActivity(intent);
                }
            });
        }

        MyReceiver myReceiver = new MyReceiver();

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_ACTION");
        registerReceiver(myReceiver, intentFilter);

        Intent intent = new Intent("android.net.conn.CONNECTIVITY_ACTION");
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_camera:
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_item_refresh:
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                getImageList(1);
                break;
        }
        return true;
    }

    private void getImageList(int userId) {
        final List<String> localImagesUrls = new ArrayList<>();

        imagesUrls.clear();

        if (!Utils.fileUrlEmpty()) {
            localImagesUrls.addAll(Utils.getLocalImages());
            imagesUrls.addAll(localImagesUrls);
        }

        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getImages(userId, new Callback<List<ImageGET>>() {
            @Override
            public void success(List<ImageGET> imageGETs, Response response) {
                if (imageGETs != null) {
                    for (ImageGET imageGET : imageGETs) {
                        imagesUrls.add(imageGET.getFile());
                    }
                }

                ImageListAdapter adapter = new ImageListAdapter(mContext, R.layout.image_list_item, imagesUrls, localImagesUrls);

                if (gridView != null) {
                    gridView.setAdapter(adapter);
                }

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (imagesUrls.size() > 0) {
                    ImageListAdapter adapter = new ImageListAdapter(mContext, R.layout.image_list_item, imagesUrls, localImagesUrls);
                    if (gridView != null) {
                        gridView.setAdapter(adapter);
                    }
                }

                Toast.makeText(mContext, "Failed to get images.", Toast.LENGTH_LONG).show();
                Log.e("GET", "Image List", error);
            }
        });
    }
}



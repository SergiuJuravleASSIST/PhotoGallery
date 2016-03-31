package ro.sergiu.photogallery.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import ro.sergiu.photogallery.R;


/**
 * Created by Sergiu on 18.03.2016.
 */
public class ImageListAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Uri> imageUrls;
    private LayoutInflater inflater;
    private List<Uri> localImageUrls;

    public ImageListAdapter(Context context, int resourceId, List<Uri> items, List<Uri> localItems){
        super(context, resourceId, items);
        mContext = context;
        imageUrls = items;
        inflater = LayoutInflater.from(context);
        localImageUrls = localItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.image_list_item, parent, false);
        }
        try {
            Uri imageUrl = imageUrls.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

            if (localImageUrls.contains(imageUrl)) {
                Picasso
                        .with(mContext)
                        .load(imageUrl)
                        .resize(300, 150)
                        .into(imageView);

                ImageView icon = (ImageView) convertView.findViewById(R.id.image_status);
                icon.setBackgroundResource(R.drawable.cloud_off);
            } else {
                Picasso
                        .with(mContext)
                        .load(imageUrl)
                        .fit()
                        .into(imageView);
            }

        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

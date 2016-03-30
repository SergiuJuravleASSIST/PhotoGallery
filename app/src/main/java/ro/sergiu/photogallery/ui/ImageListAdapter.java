package ro.sergiu.photogallery.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ro.sergiu.photogallery.R;


/**
 * Created by Sergiu on 18.03.2016.
 */
public class ImageListAdapter extends ArrayAdapter {
    private Context mContext;
    private List<String> imageUrls;
    private LayoutInflater inflater;
    private int imageNumber;

    public ImageListAdapter(Context context, int resourceId, List<String> items, int imageNumber){
        super(context, resourceId, items);
        mContext = context;
        imageUrls = items;
        inflater = LayoutInflater.from(context);
        this.imageNumber = imageNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.image_list_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        Picasso
                .with(mContext)
                .load(imageUrls.get(position))
                .fit()
                .into(imageView);

        if(position >= imageNumber) {
            ImageView icon = (ImageView) convertView.findViewById(R.id.image_status);
            icon.setBackgroundResource(R.drawable.cloud_on);
       }

        return convertView;
    }
}

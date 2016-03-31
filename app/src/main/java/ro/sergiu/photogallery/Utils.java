package ro.sergiu.photogallery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sergiu on 22.03.2016.
 */
public class Utils {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String FILE_NAME = "ImageUrls.txt";

    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public static boolean writeUrlToFile(Uri url) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File in = new File(mediaStorageDir, FILE_NAME);

        FileOutputStream fOut;

        try {
            if (!in.exists()) {
                fOut = new FileOutputStream(in.getPath());
            } else {
                fOut = new FileOutputStream(in.getPath(), true);
            }

            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            BufferedWriter outBuff = new BufferedWriter(osw);

            outBuff.write(url.toString());
            outBuff.write("\n");

            osw.flush();
            outBuff.flush();
            outBuff.close();
            osw.close();
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    public static Uri readUrlFromFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File in = new File(mediaStorageDir, FILE_NAME);
        File out = new File(mediaStorageDir, "temp.txt");

        String inputLine;

        try {
            FileInputStream fIn = new FileInputStream(in.getPath());
            InputStreamReader isr = new InputStreamReader(fIn);
            FileOutputStream fOut = new FileOutputStream(out.getPath());
            BufferedReader inBuff = new BufferedReader(isr);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            BufferedWriter outBuff = new BufferedWriter(osw);

            String url = inBuff.readLine();

            while ((inputLine = inBuff.readLine()) != null) {
                outBuff.append(inputLine);
                outBuff.append("\n");
            }

            fIn.close();
            fOut.close();
            isr.close();
            osw.close();
            inBuff.close();
            outBuff.close();

            if(in.delete())
                out.renameTo(in);
            return Uri.parse(url);
        } catch (IOException e) {
            return null;
        }
    }

    public static List<String> getLocalImages() {
        List<String> images = new ArrayList<>();
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File in = new File(mediaStorageDir, FILE_NAME);

        try {
            FileInputStream fIn = new FileInputStream(in.getPath());
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader inBuff = new BufferedReader(isr);

            String inputLine;

            while ((inputLine = inBuff.readLine()) != null) {
                images.add(inputLine);
            }
            fIn.close();
            inBuff.close();
            isr.close();

            return images;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean fileUrlEmpty() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File in = new File(mediaStorageDir, FILE_NAME);

        return in.length() == 0;
    }

    public static void deleteFile(Uri uri) {
        File f = new File(uri.getPath());
        f.delete();
    }
}

package ro.sergiu.photogallery.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import ro.sergiu.photogallery.R;
import ro.sergiu.photogallery.Utils;
import ro.sergiu.photogallery.api.Tasks;

/**
 * Created by User on 30.03.2016.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            if (!Utils.fileUrlEmpty()) {
               final NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Photo Gallery")
                                .setContentText("Sending image...");

                final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Uri imageUrl = Utils.readUrlFromFile();
                Tasks.ImageSendTask imageSendTask = new Tasks.ImageSendTask(context, imageUrl);
                imageSendTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int icr;
                        for(icr = 0; icr < 100; icr += 5) {
                            mBuilder.setProgress(100, icr, false);
                            manager.notify(1, mBuilder.build());
                        }

                        mBuilder.setProgress(0, 0, false);
                        mBuilder.setContentText("Sending complete.");

                        manager.notify(1, mBuilder.build());
                    }
                });
            }
        }
    }
}


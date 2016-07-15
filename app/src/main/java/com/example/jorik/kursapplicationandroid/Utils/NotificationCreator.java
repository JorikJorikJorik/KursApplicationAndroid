package com.example.jorik.kursapplicationandroid.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.example.jorik.kursapplicationandroid.R;

/**
 * Created by jorik on 10.07.16.
 */

public class NotificationCreator {

    private static final int ID_NOTIFICATION = 1;
    private Context mContext;

    public NotificationCreator(Context context) {
        mContext = context;
    }

    public void create(Intent intent, int image, String title, String text) {

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(image)
                .setContentTitle(title)
                .setContentText(text)
                .setSound(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.battle))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ID_NOTIFICATION, builder.build());
    }
}

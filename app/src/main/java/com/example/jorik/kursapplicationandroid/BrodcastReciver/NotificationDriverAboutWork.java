package com.example.jorik.kursapplicationandroid.BrodcastReciver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;
import com.example.jorik.kursapplicationandroid.View.Activity.WorkListActivity;

import static com.example.jorik.kursapplicationandroid.ViewModel.WorkViewModel.INFO_FOR_BROADCAST;

/**
 * Created by jorik on 04.06.16.
 */

public class NotificationDriverAboutWork extends BroadcastReceiver {

    private static final int ID_NOTIFICATION = 1;
    public static final String CANCEL_FLAG = "cancel_notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        BusDTO busInfo = intent.getParcelableExtra(INFO_FOR_BROADCAST);

        String info = "Your bus:" + busInfo.getNumber() +"/" + busInfo.getModel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.work)
                        .setContentTitle("Today you work!")
                        .setContentText(info)
                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.audio))
                        .setAutoCancel(true);

        Intent workList = new Intent(context, MainActivity.class);
        workList.putExtra(CANCEL_FLAG, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,workList , PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ID_NOTIFICATION, builder.build());


    }
}

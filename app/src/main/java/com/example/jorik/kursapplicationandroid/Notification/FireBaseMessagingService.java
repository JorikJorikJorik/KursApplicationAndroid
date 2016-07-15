package com.example.jorik.kursapplicationandroid.Notification;

import android.content.Intent;

import com.example.jorik.kursapplicationandroid.Model.Enum.NavigationItem;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.Utils.NotificationCreator;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String LOG = FireBaseMessagingService.class.getName();

    private static final String TITLE_JSON = "title";
    private static final String TEXT_JSON = "text";
    private static final String ID_JSON = "id";

    private Intent intent;
    private Integer idMessage;
    private NotificationCreator notificationCreator;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        notificationCreator = new NotificationCreator(this);
        intent = new Intent(this, MainActivity.class);

        if (remoteMessage.getData() != null) {

            if (remoteMessage.getData().get(ID_JSON) != null) {
                idMessage = Integer.parseInt(remoteMessage.getData().get(ID_JSON));
                //intent.putExtra(CONVERSATION_ID, idMessage);
            }

            intent.putExtra(MainActivity.CHOOSE_FRAGMENT, idMessage == null ? NavigationItem.WORK_LIST.getValue() : NavigationItem.CONVERSATION_LIST.getValue());

            notificationCreator.create(intent, R.mipmap.work, remoteMessage.getData().get(TITLE_JSON), remoteMessage.getData().get(TEXT_JSON));
        }
    }
}

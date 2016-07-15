package com.example.jorik.kursapplicationandroid.Notification;

import android.util.Log;

import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by jorik on 10.07.16.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {

    private final static String LOG = FireBaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG, token);
        addTokenToDataBase(token);
    }

    private void addTokenToDataBase(String token){
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
        base.setToken(token);
        base.save();
    }
}

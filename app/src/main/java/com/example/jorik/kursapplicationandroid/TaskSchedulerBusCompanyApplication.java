package com.example.jorik.kursapplicationandroid;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by jorik on 17.05.16.
 */
public class TaskSchedulerBusCompanyApplication extends Application {

    private static TaskSchedulerBusCompanyApplication mTaskSchedulerBusCompanyApplication = null;
    private ApplicationDataBase mApplicationDataBase;

    public static synchronized TaskSchedulerBusCompanyApplication getInstance(){
        if(mTaskSchedulerBusCompanyApplication == null)
            mTaskSchedulerBusCompanyApplication = new TaskSchedulerBusCompanyApplication();
        return mTaskSchedulerBusCompanyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        ActiveAndroid.initialize(this);
    }

    public ApplicationDataBase getApplicationDataBase() {
        return mApplicationDataBase;
    }

    public void setApplicationDataBase(ApplicationDataBase applicationDataBase) {
        mApplicationDataBase = applicationDataBase;
    }

    public void initDataBase(){
        ApplicationDataBase applicationDataBase = ApplicationDataBase.getInstance();
        setApplicationDataBase(applicationDataBase);
        ApplicationDataBase dataBase = applicationDataBase.getSelectDataBase();
        if(dataBase == null) getApplicationDataBase().setDefaultValues();
    }
}

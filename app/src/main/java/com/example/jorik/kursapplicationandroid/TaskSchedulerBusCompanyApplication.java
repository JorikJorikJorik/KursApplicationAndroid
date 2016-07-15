package com.example.jorik.kursapplicationandroid;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;


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

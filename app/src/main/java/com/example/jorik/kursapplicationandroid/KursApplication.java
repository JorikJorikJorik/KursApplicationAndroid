package com.example.jorik.kursapplicationandroid;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

//import com.orm.SugarContext;

/**
 * Created by jorik on 17.05.16.
 */
public class KursApplication extends Application {

    private static KursApplication mKursApplication = null;

    public static synchronized KursApplication getInstance(){
        if(mKursApplication == null)
            mKursApplication = new KursApplication();
        return mKursApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}

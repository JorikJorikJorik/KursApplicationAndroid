package com.example.jorik.kursapplicationandroid.ViewModel;

import android.databinding.BaseObservable;

/**
 * Created by jorik on 01.06.16.
 */

public class BaseViewModel extends BaseObservable {

    public interface ResponseCallback {
        void responseFromServer(Integer response);
    }
}

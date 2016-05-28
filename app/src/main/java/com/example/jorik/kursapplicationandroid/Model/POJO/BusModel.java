package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;

/**
 * Created by jorik on 19.05.16.
 */

public class BusModel {

    private Rights mRights;
    private int visibleFAB;
    private int visibleRecyclerLayout;
    private int visibleError;
    private int visibleProgress;
    private String errorString;
    private BusAdapter busAdapter;

    public int getVisibleRecyclerLayout() {
        return visibleRecyclerLayout;
    }

    public void setVisibleRecyclerLayout(int visibleRefresh) {
        this.visibleRecyclerLayout = visibleRefresh;
    }

    public int getVisibleError() {
        return visibleError;
    }

    public void setVisibleError(int visibleError) {
        this.visibleError = visibleError;
    }

    public int getVisibleProgress() {
        return visibleProgress;
    }

    public void setVisibleProgress(int visibleProgress) {
        this.visibleProgress = visibleProgress;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public BusAdapter getBusAdapter() {
        return busAdapter;
    }

    public void setBusAdapter(BusAdapter busAdapter) {
        this.busAdapter = busAdapter;
    }

    public Rights getRights() {
        return mRights;
    }

    public void setRights(Rights rights) {
        mRights = rights;
    }

    public int getVisibleFAB() {
        return visibleFAB;
    }

    public void setVisibleFAB(int visibleFAB) {
        this.visibleFAB = visibleFAB;
    }
}

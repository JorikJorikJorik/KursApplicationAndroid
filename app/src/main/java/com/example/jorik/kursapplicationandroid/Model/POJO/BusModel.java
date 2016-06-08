package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;


import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;

/**
 * Created by jorik on 19.05.16.
 */

public class BusModel {

    private Rights mRights;
    private int visibleFAB;
    private boolean completeRequest;
    private boolean visibleProgress;
    private String errorString;
    private BusAdapter mAdapter;

    public boolean getVisibleProgress() {
        return visibleProgress;
    }

    public void setVisibleProgress(boolean visibleProgress) {
        this.visibleProgress = visibleProgress;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public BusAdapter getBusAdapter() {
        return mAdapter;
    }

    public void setBusAdapter(BusAdapter mAdapter) {
        this.mAdapter = mAdapter;
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

    public boolean getCompleteRequest() {
        return completeRequest;
    }

    public void setCompleteRequest(boolean completeRequest) {
        this.completeRequest = completeRequest;
    }
}

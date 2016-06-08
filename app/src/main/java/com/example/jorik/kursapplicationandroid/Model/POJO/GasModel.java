package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.support.v7.widget.RecyclerView;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.FullGasAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.GasAdapter;

/**
 * Created by jorik on 29.05.16.
 */

public class GasModel {

    private Rights mRights;
    private int visibleFAB;
    private boolean completeRequest;
    private boolean visibleProgress;
    private String errorString;
    private FullGasAdapter mFullAdapter;

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

    public FullGasAdapter getFullAdapter() {
        return mFullAdapter;
    }

    public void setFullAdapter(FullGasAdapter fullAdapter) {
        mFullAdapter = fullAdapter;
    }
}

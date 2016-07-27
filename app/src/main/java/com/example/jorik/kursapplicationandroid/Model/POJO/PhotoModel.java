package com.example.jorik.kursapplicationandroid.Model.POJO;

import com.example.jorik.kursapplicationandroid.View.Adapter.PhotoAdapter;

/**
 * Created by jorik on 29.05.16.
 */

public class PhotoModel {

    private int visibleFAB;
    private boolean completeRequest;
    private boolean visibleProgress;
    private String errorString;
    private PhotoAdapter mPhotoAdapter;

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

    public PhotoAdapter getPhotoAdapter() {
        return mPhotoAdapter;
    }

    public void setPhotoAdapter(PhotoAdapter photoAdapter) {
        mPhotoAdapter = photoAdapter;
    }
}

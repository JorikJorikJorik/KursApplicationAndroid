package com.example.jorik.kursapplicationandroid.Model.POJO;

import com.example.jorik.kursapplicationandroid.View.Adapter.FriendsAdapter;

/**
 * Created by jorik on 29.05.16.
 */

public class FriendsModel {

    private boolean completeRequest;
    private boolean visibleProgress;
    private String errorString;
    private FriendsAdapter mAdapter;

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

    public FriendsAdapter getFriendsAdapter() {
        return mAdapter;
    }

    public void setFriendsAdapter(FriendsAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }


    public boolean getCompleteRequest() {
        return completeRequest;
    }

    public void setCompleteRequest(boolean completeRequest) {
        this.completeRequest = completeRequest;
    }

}

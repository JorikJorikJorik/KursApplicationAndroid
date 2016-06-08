package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.support.v7.widget.RecyclerView;

import com.example.jorik.kursapplicationandroid.Model.Enum.StateCreateWorkList;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;

/**
 * Created by jorik on 01.06.16.
 */

public class CreateWorkModel {

    private RecyclerView.Adapter<?> mAdapter;
    private String errorString;
    private StateCreateWorkList mStateCreateWorkList;
    private boolean completeRequest;
    private int visibleChooseDate;
    private boolean visibleProgress;
    private String title;
    private boolean enableSendButton;

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public StateCreateWorkList getStateCreateWorkList() {
        return mStateCreateWorkList;
    }

    public void setStateCreateWorkList(StateCreateWorkList stateCreateWorkList) {
        mStateCreateWorkList = stateCreateWorkList;
    }

    public boolean getCompleteRequest() {
        return completeRequest;
    }

    public void setCompleteRequest(boolean completeRequest) {
        this.completeRequest = completeRequest;
    }

    public int getVisibleChooseDate() {
        return visibleChooseDate;
    }

    public void setVisibleChooseDate(int visibleChooseDate) {
        this.visibleChooseDate = visibleChooseDate;
    }

    public boolean getVisibleProgress() {
        return visibleProgress;
    }

    public void setVisibleProgress(boolean visibleProgress) {
        this.visibleProgress = visibleProgress;
    }

    public RecyclerView.Adapter<?> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        mAdapter = adapter;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getEnableSendButton() {
        return enableSendButton;
    }

    public void setEnableSendButton(boolean enableSendButton) {
        this.enableSendButton = enableSendButton;
    }
}

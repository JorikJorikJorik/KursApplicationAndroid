package com.example.jorik.kursapplicationandroid.Model.POJO;

import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.View.Adapter.FullGasAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.WorkListAdapter;

/**
 * Created by jorik on 01.06.16.
 */

public class WorkModel {

    private Rights mRights;
    private int visibleFAB;
    private boolean completeRequest;
    private boolean visibleProgress;
    private String errorString;
    private WorkListAdapter mFullWorkAdapter;
    private int driverIdBottomSheet;
    private int busIdBottomSheet;

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

    public WorkListAdapter getFullWorkAdapter() {
        return mFullWorkAdapter;
    }

    public void setFullWorkAdapter(WorkListAdapter fullAdapter) {
        mFullWorkAdapter = fullAdapter;
    }

    public int getDriverIdBottomSheet() {
        return driverIdBottomSheet;
    }

    public void setDriverIdBottomSheet(int driverIdBottomSheet) {
        this.driverIdBottomSheet = driverIdBottomSheet;
    }

    public int getBusIdBottomSheet() {
        return busIdBottomSheet;
    }

    public void setBusIdBottomSheet(int busIdBottomSheet) {
        this.busIdBottomSheet = busIdBottomSheet;
    }
}

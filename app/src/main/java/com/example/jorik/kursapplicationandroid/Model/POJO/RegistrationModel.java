package com.example.jorik.kursapplicationandroid.Model.POJO;

/**
 * Created by jorik on 05.06.16.
 */

public class RegistrationModel {

    private boolean visibleProgress;
    private String errorString;
    private boolean completeRequest;

    public boolean getCompleteRequest() {
        return completeRequest;
    }

    public void setCompleteRequest(boolean completeRequest) {
        this.completeRequest = completeRequest;
    }

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
}

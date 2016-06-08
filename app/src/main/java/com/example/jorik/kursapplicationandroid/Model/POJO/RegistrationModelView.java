package com.example.jorik.kursapplicationandroid.Model.POJO;

import rx.Observable;

/**
 * Created by jorik on 05.06.16.
 */

public class RegistrationModelView {

    private String errorValidationName;
    private String errorValidationPassword;
    private String errorValidationConfirmPassword;
    private String errorValidationExperience;
    private String errorValidationSalary;

    private boolean enableSendButton;

    public String getErrorValidationName() {
        return errorValidationName;
    }

    public void setErrorValidationName(String errorValidationName) {
        this.errorValidationName = errorValidationName;
    }

    public String getErrorValidationPassword() {
        return errorValidationPassword;
    }

    public void setErrorValidationPassword(String errorValidationPassword) {
        this.errorValidationPassword = errorValidationPassword;
    }

    public String getErrorValidationConfirmPassword() {
        return errorValidationConfirmPassword;
    }

    public void setErrorValidationConfirmPassword(String errorValidationConfirmPassword) {
        this.errorValidationConfirmPassword = errorValidationConfirmPassword;
    }

    public String getErrorValidationExperience() {
        return errorValidationExperience;
    }

    public void setErrorValidationExperience(String errorValidationExperience) {
        this.errorValidationExperience = errorValidationExperience;
    }

    public String getErrorValidationSalary() {
        return errorValidationSalary;
    }

    public void setErrorValidationSalary(String errorValidationSalary) {
        this.errorValidationSalary = errorValidationSalary;
    }

    public boolean isEnableSendButton() {
        return enableSendButton;
    }

    public void setEnableSendButton(boolean enableSendButton) {
        this.enableSendButton = enableSendButton;
    }
}

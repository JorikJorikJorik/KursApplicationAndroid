package com.example.jorik.kursapplicationandroid.Model.POJO;

/**
 * Created by jorik on 29.06.16.
 */

public class SignInModel {

    private String name;
    private String password;

    private String errorValidationName;
    private String errorValidationPassword;
    private boolean enableSignInButton;

    private Integer number;
    private boolean finishActivity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public boolean getEnableSignInButton() {
        return enableSignInButton;
    }

    public void setEnableSignInButton(boolean enableSignInButton) {
        this.enableSignInButton = enableSignInButton;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isFinishActivity() {
        return finishActivity;
    }

    public void setFinishActivity(boolean finishActivity) {
        this.finishActivity = finishActivity;
    }


}

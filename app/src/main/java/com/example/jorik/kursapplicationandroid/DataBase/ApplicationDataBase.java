package com.example.jorik.kursapplicationandroid.DataBase;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateWork;
import com.example.jorik.kursapplicationandroid.R;


@Table(name = "ApplicationDataBase")
public class ApplicationDataBase extends Model {

    private static final long DEFAULT_START_HOUR = 28800000;
    private static final long DEFAULT_REPEAT_HOUR = 7200000;

    private static ApplicationDataBase mApplicationDataBase = null;

    @Column(name = "StateApplication")
    public StateApplication mStateApplication;

    @Column(name = "Role")
    public Role mRole;

    @Column(name = "Name")
    public String name;

    @Column(name = "StartTime")
    public long startTime;

    @Column(name = "RepeatTime")
    public long repeatTime;

    @Column(name = "StateWork")
    public StateWork mStateWork;

    @Column(name = "NumberUser")
    public int numberUser;

    @Column(name = "DateManager")
    public long dateManager;

    @Column(name = "Token")
    public String token;

    @Column(name = "ImageUser")
    public String imageUser;

    public ApplicationDataBase() {
    }

    public static synchronized ApplicationDataBase getInstance() {
        if (mApplicationDataBase == null)
            mApplicationDataBase = new ApplicationDataBase();
        return mApplicationDataBase;
    }

    public StateApplication getStateApplication() {
        return mStateApplication;
    }

    public void setStateApplication(StateApplication stateApplication) {
        mStateApplication = stateApplication;
    }

    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(long repeatTime) {
        this.repeatTime = repeatTime;
    }

    public StateWork getStateWork() {
        return mStateWork;
    }

    public void setStateWork(StateWork stateWork) {
        mStateWork = stateWork;
    }

    public int getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(int numberUser) {
        this.numberUser = numberUser;
    }

    public long getDateManager() {
        return dateManager;
    }

    public void setDateManager(long dateManager) {
        this.dateManager = dateManager;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public ApplicationDataBase getSelectDataBase() {
        return new Select().from(ApplicationDataBase.class).executeSingle();
    }

    public void setDefaultValues() {
        mApplicationDataBase.setName("Default"/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.setStateApplication(StateApplication.REGISTRATION);
        mApplicationDataBase.setRole(Role.NONE);
        mApplicationDataBase.setStartTime(DEFAULT_START_HOUR);
        mApplicationDataBase.setRepeatTime(DEFAULT_REPEAT_HOUR);
        mApplicationDataBase.setStateWork(StateWork.NONE);
        mApplicationDataBase.setDateManager(0);
        mApplicationDataBase.setNumberUser(0);
        mApplicationDataBase.setToken("Default"/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.setImageUser(null/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.save();
    }


}


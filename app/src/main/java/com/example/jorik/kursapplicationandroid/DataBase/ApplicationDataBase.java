package com.example.jorik.kursapplicationandroid.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;


@Table(name = "ApplicationDataBase")
public class ApplicationDataBase extends Model {

    private static final long DEFAULT_START_HOUR = 28800000;
    private static final long DEFAULT_REPEAT_HOUR = 7200000;

    private static ApplicationDataBase mApplicationDataBase = null;

    @Column(name = "StateApplication")
    public StateApplication mStateApplication;

    @Column(name = "Name")
    public String name;

    @Column(name = "StartTime")
    public long startTime;

    @Column(name = "RepeatTime")
    public long repeatTime;

    @Column(name = "NumberUser")
    public int numberUser;

    @Column(name = "DateManager")
    public long dateManager;

    @Column(name = "Token")
    public String token;

    @Column(name = "ImageUser")
    public String imageUser;

    @Column(name = "Email")
    public String email;

    @Column(name = "Location")
    public String location;

    @Column(name = "IdUser")
    public String IdUser;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public ApplicationDataBase getSelectDataBase() {
        return new Select().from(ApplicationDataBase.class).executeSingle();
    }

    public void setDefaultValues() {
        mApplicationDataBase.setName("Default"/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.setStateApplication(StateApplication.REGISTRATION);
        mApplicationDataBase.setStartTime(DEFAULT_START_HOUR);
        mApplicationDataBase.setRepeatTime(DEFAULT_REPEAT_HOUR);
        mApplicationDataBase.setDateManager(0);
        mApplicationDataBase.setNumberUser(0);
        mApplicationDataBase.setToken("Default"/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.setEmail("Default");
        mApplicationDataBase.setLocation("Default");
        mApplicationDataBase.setIdUser("Default");
        mApplicationDataBase.setImageUser(null/*context.getResources().getString(R.string.default_value_string)*/);
        mApplicationDataBase.save();
    }


}


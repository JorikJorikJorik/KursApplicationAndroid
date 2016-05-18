package com.example.jorik.kursapplicationandroid.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;



    @Table(name = "StateApplicationDataBase")
    public class StateApplicationDataBase extends Model {
        @Column(name = "Role")
        public Role mRole;

        @Column(name = "StateApplication")
        public StateApplication mStateApplication;


    public StateApplicationDataBase() {}

    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public StateApplication getStateApplication() {
        return mStateApplication;
    }

    public void setStateApplication(StateApplication stateApplication) {
        mStateApplication = stateApplication;
    }

    @Override
    public String toString() {
        return "StateApplicationDataBase{" +
                "mRole=" + mRole +
                ", mStateApplication=" + mStateApplication +
                '}';
    }
    }


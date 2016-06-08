package com.example.jorik.kursapplicationandroid.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.RegistrationActivityFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.SignInActivityFragment;

public class RegistrationActivity extends AppCompatActivity {

    public static final String REGISTRATION_STATE = "registration_state";
    private int registrationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        registrationValue  = getIntent().getIntExtra(REGISTRATION_STATE, 0);

        if(chooseFragmentByState() != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_registration, chooseFragmentByState())
                    .commit();
        }

    }

    private Fragment chooseFragmentByState(){
        StateApplication mState = StateApplication.fromValue(registrationValue);
        switch (mState) {
            case REGISTRATION:
                return RegistrationActivityFragment.newInstance();
            case SIGNIN:
                return SignInActivityFragment.newInstance();
        }
        return null;
    }

}

package com.example.jorik.kursapplicationandroid.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.RegistrationActivityFragment;

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
        createFragment();

    }

    private void createFragment() {
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
                setTitle(this.getString(R.string.title_activity_registration));
                return RegistrationActivityFragment.newInstance();
        }
        return null;
    }


}

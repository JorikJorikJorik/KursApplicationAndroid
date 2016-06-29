package com.example.jorik.kursapplicationandroid.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.BaseFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.RegistrationActivityFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.SignInActivityFragment;

public class RegistrationActivity extends AppCompatActivity implements BaseFragment.MenuChangeCallback{

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
            case SIGNIN:
                setTitle(this.getString(R.string.title_activity_sign_in));
                return SignInActivityFragment.newInstance();
        }
        return null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.registration_menu_button);
        item.setTitle(getString(registrationValue == StateApplication.REGISTRATION.getValue() ? R.string.title_activity_sign_in: R.string.title_activity_registration));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void changeMenu(StateApplication stateApplication) {
        registrationValue = stateApplication.getValue();
        createFragment();
    }
}

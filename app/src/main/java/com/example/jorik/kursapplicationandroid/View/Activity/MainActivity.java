package com.example.jorik.kursapplicationandroid.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.TaskSchedulerBusCompanyApplication;
import com.example.jorik.kursapplicationandroid.View.Fragment.FriendsListFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.PhotoListFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.UserInfoFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ApplicationDataBase mApplicationDataBase;

    private TaskSchedulerBusCompanyApplication taskSchedulerBusCompanyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        taskSchedulerBusCompanyApplication = TaskSchedulerBusCompanyApplication.getInstance();

        new Thread(() -> {
            taskSchedulerBusCompanyApplication.initDataBase();
            mApplicationDataBase = taskSchedulerBusCompanyApplication.getApplicationDataBase();
            validationStateApplication();
        }).start();


        setSupportActionBar(mToolbar);

        mNavigationView.setNavigationItemSelectedListener(item -> {

            if (item.isChecked()) item.setCheckable(false);
            else item.setCheckable(true);

            mDrawerLayout.closeDrawers();

            return chooseFragmentById(item.getItemId());
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private boolean chooseFragmentById(int item) {
        switch (item) {
            case R.id.profile_item:
                enterInFragment(UserInfoFragment.newInstance());
                setTitle(getString(R.string.profile_title));

                return true;

            case R.id.friends_item:
                enterInFragment(FriendsListFragment.newInstance());
                setTitle(getString(R.string.friends_title));

                return true;

            case R.id.pictures_item:
                enterInFragment(PhotoListFragment.newInstance());
                setTitle(getString(R.string.pictures_title));

                return true;

            default:
                return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validationStateApplication() {
        ApplicationDataBase dataBase = ApplicationDataBase.getInstance().getSelectDataBase();

        if (dataBase.getStateApplication() == StateApplication.NONE || dataBase.getStateApplication() == StateApplication.REGISTRATION) {
            moveToRegistrationActivity(StateApplication.REGISTRATION);
        } else if (dataBase.getStateApplication() == StateApplication.ENTER) {
            runOnUiThread(this::changeNameAndProfessional);
            enterInFragment(UserInfoFragment.newInstance());
            runOnUiThread(() -> setTitle(getString(R.string.profile_title)));
        }
    }

    private void changeNameAndProfessional() {
        View view = mNavigationView.getHeaderView(0);
        TextView nameText = (TextView) view.findViewById(R.id.name_text);
        nameText.setText(getString(R.string.dev_info));
    }

    private void moveToRegistrationActivity(StateApplication state) {
        Intent toRegistrationIntent = new Intent(this, RegistrationActivity.class);
        toRegistrationIntent.putExtra(RegistrationActivity.REGISTRATION_STATE, state.getValue());
        startActivity(toRegistrationIntent);
        finish();
    }

    private void signOut() {
        ApplicationDataBase dataBase = ApplicationDataBase.getInstance().getSelectDataBase();
        dataBase.setDefaultValues();
        dataBase.setStateApplication(StateApplication.REGISTRATION);
        dataBase.save();
        moveToRegistrationActivity(StateApplication.REGISTRATION);
    }

    private void enterInFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

}


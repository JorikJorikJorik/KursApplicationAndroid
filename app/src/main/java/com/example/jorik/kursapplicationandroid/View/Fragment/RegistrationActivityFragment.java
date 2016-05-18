package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegistrationActivityFragment extends Fragment {

    public RegistrationActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }
}

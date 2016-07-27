package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.RegistrationViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentRegistrationBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegistrationActivityFragment extends Fragment {

    private FragmentRegistrationBinding mFragmentRegistrationBinding;
    private RegistrationViewModel mRegistrationViewModel;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;

    public static RegistrationActivityFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationActivityFragment fragment = new RegistrationActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mFragmentRegistrationBinding = DataBindingUtil.bind(view);
        mRegistrationViewModel = new RegistrationViewModel(getActivity());
        mFragmentRegistrationBinding.setRegistration(mRegistrationViewModel);

        mFragmentRegistrationBinding.loginButton.setFragment(this);
        mFragmentRegistrationBinding.loginButton.setReadPermissions("user_friends");
        mFragmentRegistrationBinding.loginButton.registerCallback(mCallbackManager, mRegistrationViewModel.createFacebookLoginCallback(accessToken));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        mRegistrationViewModel.unsubscribe();
    }


}


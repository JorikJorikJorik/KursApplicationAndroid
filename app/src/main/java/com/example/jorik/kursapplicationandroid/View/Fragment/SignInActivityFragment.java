package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.RegistrationViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.SignInViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentRegistrationBinding;
import com.example.jorik.kursapplicationandroid.databinding.FragmentSignInBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignInActivityFragment extends BaseFragment {

    private FragmentSignInBinding mFragmentSignInBinding;
    private SignInViewModel mSignInViewModel;
    private MenuChangeCallback callback;

    public static SignInActivityFragment newInstance() {
        Bundle args = new Bundle();
        SignInActivityFragment fragment = new SignInActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mFragmentSignInBinding = DataBindingUtil.bind(view);
        mSignInViewModel = new SignInViewModel(getActivity());
        mFragmentSignInBinding.setSignIn(mSignInViewModel);

        mFragmentSignInBinding.signinFab.setOnClickListener(v -> {
            mSignInViewModel.moveToWorkWithApplication();
            if (mSignInViewModel.isFinishActivity())
                getActivity().finish();
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        callback = (MenuChangeCallback) activity;
        super.onAttach(activity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.registration_menu_button) {
            callback.changeMenu(StateApplication.REGISTRATION);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        mSignInViewModel.unsubscribe();
        super.onDestroy();
    }
}

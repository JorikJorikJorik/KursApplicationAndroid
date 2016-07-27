package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.UserInfoViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentUserInfoBinding;
import com.facebook.AccessToken;


public class UserInfoFragment extends Fragment {

    private UserInfoViewModel mUserInfoViewModel;
    private FragmentUserInfoBinding mFragmentUserInfoBinding;

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        mFragmentUserInfoBinding = DataBindingUtil.bind(view);
        mUserInfoViewModel = new UserInfoViewModel(getActivity());
        mFragmentUserInfoBinding.setUser(mUserInfoViewModel);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mUserInfoViewModel.getInformationUserFromFacebook(AccessToken.getCurrentAccessToken().getToken());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserInfoViewModel.unsubscribe();
    }
}

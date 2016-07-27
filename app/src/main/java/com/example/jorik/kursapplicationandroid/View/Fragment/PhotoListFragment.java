package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.PhotoViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentPhotoListBinding;

/**
 * Created by jorik on 29.05.16.
 */

public class PhotoListFragment extends Fragment {

    private static final int COUNT_COLUMN = 2;

    private PhotoViewModel mPhotoViewModel;
    private FragmentPhotoListBinding mFragmentPhotoListBinding;

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);

        mFragmentPhotoListBinding = DataBindingUtil.bind(view);

        mPhotoViewModel = new PhotoViewModel(getActivity(), mFragmentPhotoListBinding.photoSwipeRefresh);
        mFragmentPhotoListBinding.setPhotoViewModel(mPhotoViewModel);
        mFragmentPhotoListBinding.photoRecycler.setLayoutManager(new GridLayoutManager(getActivity(), COUNT_COLUMN));

        mFragmentPhotoListBinding.photoSwipeRefresh.setColorSchemeColors(Color.BLUE);


        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mPhotoViewModel.friendsPhoto();
    }

    @Override
    public void onDestroy() {
        mPhotoViewModel.unsubscribe();
        super.onDestroy();
    }


}
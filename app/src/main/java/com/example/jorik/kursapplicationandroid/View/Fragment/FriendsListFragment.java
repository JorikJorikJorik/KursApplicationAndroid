package com.example.jorik.kursapplicationandroid.View.Fragment;


import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.FriendsViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentFriendsListBinding;

public class FriendsListFragment extends Fragment {

    private FriendsViewModel mFriendsViewModel;
    private FragmentFriendsListBinding mFragmentFriendsListBinding;

    public static FriendsListFragment newInstance() {
        FriendsListFragment fragment = new FriendsListFragment();
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
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        mFragmentFriendsListBinding = DataBindingUtil.bind(view);
        mFriendsViewModel = new FriendsViewModel(getActivity(), mFragmentFriendsListBinding.friendsSwipeRefresh);
        mFragmentFriendsListBinding.setFriends(mFriendsViewModel);

        mFragmentFriendsListBinding.friendsSwipeRefresh.setColorSchemeColors(Color.BLUE);
        mFragmentFriendsListBinding.friendsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mFriendsViewModel.getFriendsList();
    }

    @Override
    public void onDestroy() {
        mFriendsViewModel.unsubscribe();
        super.onDestroy();
    }


}
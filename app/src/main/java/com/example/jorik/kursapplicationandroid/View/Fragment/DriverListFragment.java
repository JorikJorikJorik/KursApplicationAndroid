package com.example.jorik.kursapplicationandroid.View.Fragment;


import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;
import com.example.jorik.kursapplicationandroid.ViewModel.DriverViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentDriverListBinding;

import java.util.ArrayList;
import java.util.List;

public class DriverListFragment extends BaseFragment {

    private static final String ARGUMENTS_RIGHTS_DRIVER = "rights_for_driver";

    private Rights mRights;
    private DriverViewModel mDriverItemViewModel;
    private FragmentDriverListBinding mFragmentDriverListBinding;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private AdapterFragmentCallback mCallback;

    public static DriverListFragment newInstance(Rights rights) {
        DriverListFragment fragment = new DriverListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_RIGHTS_DRIVER, rights.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRights = Rights.fromValue(getArguments().getInt(ARGUMENTS_RIGHTS_DRIVER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);

        mFragmentDriverListBinding = DataBindingUtil.bind(view);
        mDriverItemViewModel = new DriverViewModel(getActivity(), mFragmentDriverListBinding.refreshDriverList, mRights);
        mFragmentDriverListBinding.setDriverViewModel(mDriverItemViewModel);

        mFragmentDriverListBinding.driverRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentDriverListBinding.refreshDriverList.setColorSchemeColors(Color.BLUE);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int i = viewHolder.getPosition();
                mDriverItemViewModel.deleteDriver(i);
                mCallback = (AdapterFragmentCallback) mDriverItemViewModel.getDriverAdapter();
                mCallback.deleteItem(viewHolder.getPosition());
            }

        };

        if (mDriverItemViewModel.getRightsDriver() == Rights.CHANGE) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(mFragmentDriverListBinding.driverRecycler);
        }

        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mDriverItemViewModel.getAllDataDriver();
    }

    @Override
    public void onDestroy() {
        mDriverItemViewModel.unsubscribeRequest();
        super.onDestroy();
    }


}
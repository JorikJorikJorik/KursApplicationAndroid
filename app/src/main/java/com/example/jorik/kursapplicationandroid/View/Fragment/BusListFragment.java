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

import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.BusViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.CreateBusViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentBusListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 */
public class BusListFragment extends Fragment{

    private static final String ARGUMENTS_RIGHTS = "rights";

    private Rights mRights;
    private BusViewModel mBusItemViewModel;
    private FragmentBusListBinding mFragmentBusListBinding;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private BusFragmentCallback mCallback;

    public BusListFragment() {

    }

    public static BusListFragment newInstance(Rights rights) {
        BusListFragment fragment = new BusListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENTS_RIGHTS, rights.getValue());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int value = getArguments().getInt(ARGUMENTS_RIGHTS);
        mRights = Rights.fromValue(value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_list, container, false);

        mFragmentBusListBinding = DataBindingUtil.bind(view);
        mBusItemViewModel = new BusViewModel(getActivity(), mFragmentBusListBinding.refreshBusList, mRights);
        mFragmentBusListBinding.setBusViewModel(mBusItemViewModel);

        mFragmentBusListBinding.busRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFragmentBusListBinding.busAddFab.setOnClickListener(v -> mBusItemViewModel.moveToCreateActivity());
        mFragmentBusListBinding.errorText.setOnClickListener(v -> mBusItemViewModel.getAllDataRequest());
        mFragmentBusListBinding.refreshBusList.setOnRefreshListener(() -> mBusItemViewModel.getAllDataRequest());

        mFragmentBusListBinding.refreshBusList.setColorSchemeColors(Color.BLUE);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mBusItemViewModel.deleteItemRequest(viewHolder.getPosition());
                mCallback = mBusItemViewModel.getBusAdapter();
                mCallback.deleteItem(viewHolder.getPosition());
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mFragmentBusListBinding.busRecycler);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mBusItemViewModel.getAllDataRequest();
    }

    @Override
    public void onDestroy() {
        mBusItemViewModel.unsubscribeRequest();
        super.onDestroy();
    }

    public interface BusFragmentCallback {
        void deleteItem(int position);
    }

}
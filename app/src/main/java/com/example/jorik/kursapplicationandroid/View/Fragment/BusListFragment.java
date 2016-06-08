package com.example.jorik.kursapplicationandroid.View.Fragment;


import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.BusViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentBusListBinding;

public class BusListFragment extends BaseFragment {

    private static final String ARGUMENTS_RIGHTS_BUS = "rights_for_Bus";

    private Rights mRights;
    private BusViewModel mBusItemViewModel;
    private FragmentBusListBinding mFragmentBusListBinding;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private AdapterFragmentCallback mCallback;

    public static BusListFragment newInstance(Rights rights) {
        BusListFragment fragment = new BusListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_RIGHTS_BUS, rights.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRights = Rights.fromValue(getArguments().getInt(ARGUMENTS_RIGHTS_BUS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_list, container, false);

        mFragmentBusListBinding = DataBindingUtil.bind(view);

        mBusItemViewModel = new BusViewModel(getActivity(), mFragmentBusListBinding.refreshBusList, mRights);

        mFragmentBusListBinding.setBusViewModel(mBusItemViewModel);
        mFragmentBusListBinding.busRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentBusListBinding.refreshBusList.setColorSchemeColors(Color.BLUE);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int i = viewHolder.getPosition();
                mBusItemViewModel.deleteBus(i);
                mCallback = (AdapterFragmentCallback) mBusItemViewModel.getBusAdapter();
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
        mBusItemViewModel.getAllDataBus();
    }

    @Override
    public void onDestroy() {
        mBusItemViewModel.unsubscribeRequest();
        super.onDestroy();
    }

}
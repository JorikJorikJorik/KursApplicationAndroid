package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateWork;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.RepairViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentRepairListBinding;

/**
 * Created by jorik on 29.05.16.
 */

public class RepairListFragment extends BaseFragment {

    private static final String ARGUMENTS_RIGHTS_REPAIR = "rights_for_repair";

    private Rights mRights;
    private RepairViewModel mRepairItemViewModel;
    private FragmentRepairListBinding mFragmentRepairListBinding;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private AdapterFragmentCallback mCallback;

    public static RepairListFragment newInstance(Rights rights) {
        RepairListFragment fragment = new RepairListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_RIGHTS_REPAIR, rights.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRights = Rights.fromValue(getArguments().getInt(ARGUMENTS_RIGHTS_REPAIR));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair_list, container, false);

        ApplicationDataBase dataBase = ApplicationDataBase.getInstance().getSelectDataBase();

        mFragmentRepairListBinding = DataBindingUtil.bind(view);

        mRepairItemViewModel = new RepairViewModel(getActivity(), mFragmentRepairListBinding.refreshRepairList, mRights);

        mFragmentRepairListBinding.setRepairViewModel(mRepairItemViewModel);
        mFragmentRepairListBinding.repairRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentRepairListBinding.refreshRepairList.setColorSchemeColors(Color.BLUE);

        mFragmentRepairListBinding.repairAddFab.setVisibility(View.GONE);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mRepairItemViewModel.deleteRepair(viewHolder.getPosition());
                mCallback = (AdapterFragmentCallback) mRepairItemViewModel.getRepairAdapter();
                mCallback.deleteItem(viewHolder.getPosition());
            }

        };

        if (mRepairItemViewModel.getRightsRepair() == Rights.CHANGE  && dataBase.getStateWork() == StateWork.WORK_TODAY) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(mFragmentRepairListBinding.repairRecycler);
        }
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mRepairItemViewModel.getAllDataRepair();
    }

    @Override
    public void onDestroy() {
        mRepairItemViewModel.unsubscribeRequest();
        super.onDestroy();
    }

}

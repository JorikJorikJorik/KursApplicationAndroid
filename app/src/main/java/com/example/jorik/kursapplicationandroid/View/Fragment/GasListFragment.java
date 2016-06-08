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
import com.example.jorik.kursapplicationandroid.ViewModel.GasViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentGasListBinding;

/**
 * Created by jorik on 29.05.16.
 */

public class GasListFragment extends BaseFragment {

    private static final String ARGUMENTS_RIGHTS_GAS = "rights_for_gas";

    private Rights mRights;
    private GasViewModel mGasItemViewModel;
    private FragmentGasListBinding mFragmentGasListBinding;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private AdapterFragmentCallback mCallback;

    public static GasListFragment newInstance(Rights rights) {
        GasListFragment fragment = new GasListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_RIGHTS_GAS, rights.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRights = Rights.fromValue(getArguments().getInt(ARGUMENTS_RIGHTS_GAS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gas_list, container, false);

        ApplicationDataBase dataBase = ApplicationDataBase.getInstance().getSelectDataBase();

        mFragmentGasListBinding = DataBindingUtil.bind(view);

        mGasItemViewModel = new GasViewModel(getActivity(), mFragmentGasListBinding.refreshGasList, mRights);

        mFragmentGasListBinding.setGasViewModel(mGasItemViewModel);
        mFragmentGasListBinding.gasRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentGasListBinding.refreshGasList.setColorSchemeColors(Color.BLUE);


        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mGasItemViewModel.deleteGas(viewHolder.getPosition());
                mCallback = (AdapterFragmentCallback) mGasItemViewModel.getFullGasAdapter();
                mCallback.deleteItem(viewHolder.getPosition());
            }

        };

        if(mGasItemViewModel.getRightsGas() == Rights.CHANGE  && dataBase.getStateWork() == StateWork.WORK_TODAY) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(mFragmentGasListBinding.gasRecycler);
        }

        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        mGasItemViewModel.getAllDataGas();
    }

    @Override
    public void onDestroy() {
        mGasItemViewModel.unsubscribeRequest();
        super.onDestroy();
    }



}
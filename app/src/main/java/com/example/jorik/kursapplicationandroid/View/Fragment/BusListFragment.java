package com.example.jorik.kursapplicationandroid.View.Fragment;


import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.BusViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentBusListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 */
public class BusListFragment extends Fragment {


    private Rights mRights;
    private List<BusDTO> listBusDTO;
    private BusViewModel mBusItemViewModel;
    private FragmentBusListBinding mFragmentBusListBinding;

    private FloatingActionButton addBusButton;
    private RecyclerView busRecyclerView;

    public BusListFragment(Rights rights){
        mRights = rights;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_list, container, false);

        busRecyclerView = (RecyclerView) view.findViewById(R.id.bus_recycler);
        addBusButton = (FloatingActionButton) view.findViewById(R.id.bus_add_fab);


        mBusItemViewModel = new BusViewModel(getActivity());

        mFragmentBusListBinding = DataBindingUtil.bind(view);
        mFragmentBusListBinding.setBusViewModel(mBusItemViewModel);

        listBusDTO = new ArrayList<>();

        addBusButton.setOnClickListener(v -> {

        });

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

}

package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorik on 17.05.16.
 */
public class DriverListFragment extends Fragment {

    private Rights mRights;
    private List<DriverDTO> mDriverDTOlist;
    private DriverAdapter mDriverAdapter;

    private RecyclerView driverRecyclerView;
    private FloatingActionButton addDriverButton;

    public DriverListFragment(Rights rights) {
        mRights = rights;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus_list, container, false);

        mDriverDTOlist = new ArrayList<>();
        driverRecyclerView = (RecyclerView) view.findViewById(R.id.driver_recycler);
        addDriverButton = (FloatingActionButton) view.findViewById(R.id.driver_add_fab);

        addDriverButton.setOnClickListener(v -> {

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

package com.example.jorik.kursapplicationandroid.View.Fragment.Create;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.BaseFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.Create.CreateRepairViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentCreateRepairDataBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class CreateRepairDataActivityFragment extends Fragment implements CreateRepairViewModel.ResponseCallback {

    private FragmentCreateRepairDataBinding mFragmentCreateRepairDataBinding;
    private CreateRepairViewModel mCreateRepairViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getString(R.string.create_repair_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_repair_data, container, false);

        mCreateRepairViewModel = new CreateRepairViewModel(getActivity(), CreateRepairDataActivityFragment.this);
        mFragmentCreateRepairDataBinding = DataBindingUtil.bind(view);
        mFragmentCreateRepairDataBinding.setRepair(mCreateRepairViewModel);

        mFragmentCreateRepairDataBinding.typeRepairSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCreateRepairViewModel.setCondition(getActivity().getResources().getStringArray(R.array.bus_condition)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mCreateRepairViewModel.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void responseFromServer(Integer response) {
        if (response.equals(200)) {
            Toast.makeText(getActivity(), "New repair blank created", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        else Snackbar.make(mFragmentCreateRepairDataBinding.repairCreateCoordinationLayout, "Error response", Snackbar.LENGTH_SHORT)
                .setAction("Refresh", v -> mCreateRepairViewModel.sendRequestByCreate()).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


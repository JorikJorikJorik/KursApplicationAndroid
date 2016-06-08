package com.example.jorik.kursapplicationandroid.View.Fragment.Create;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.Create.CreateGasViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentCreateGasDataBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class CreateGasDataActivityFragment extends Fragment implements CreateGasViewModel.ResponseCallback {

    private FragmentCreateGasDataBinding mFragmentCreateGasDataBinding;
    private CreateGasViewModel mCreateGasViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getString(R.string.create_gas_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_gas_data, container, false);

        mCreateGasViewModel = new CreateGasViewModel(getActivity(), CreateGasDataActivityFragment.this);
        mFragmentCreateGasDataBinding = DataBindingUtil.bind(view);
        mFragmentCreateGasDataBinding.setGas(mCreateGasViewModel);

        mFragmentCreateGasDataBinding.typeGasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCreateGasViewModel.setType(getActivity().getResources().getStringArray(R.array.gas_type)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mCreateGasViewModel.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void responseFromServer(Integer response) {
        if (response.equals(200)) {
            Toast.makeText(getActivity(), "New gas blank created", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        else Snackbar.make(mFragmentCreateGasDataBinding.gasCreateCoordinationLayout, "Error response", Snackbar.LENGTH_SHORT)
                    .setAction("Refresh", v -> mCreateGasViewModel.sendRequestByCreate()).show();
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

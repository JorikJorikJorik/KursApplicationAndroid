package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.RegistrationViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentRegistrationBinding;

import retrofit.http.Body;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegistrationActivityFragment extends Fragment {

    private FragmentRegistrationBinding mFragmentRegistrationBinding;
    private RegistrationViewModel mRegistrationViewModel;

    public static RegistrationActivityFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationActivityFragment fragment = new RegistrationActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_registration, container, false);

        mFragmentRegistrationBinding = DataBindingUtil.bind(view);
        mRegistrationViewModel = new RegistrationViewModel(getActivity(), mFragmentRegistrationBinding.driverCheckRegistreation);
        mFragmentRegistrationBinding.setRegistration(mRegistrationViewModel);

        mFragmentRegistrationBinding.registrationFab.setOnClickListener(v -> {
            mRegistrationViewModel.moveToWorkWithApplication();
            getActivity().finish();
        });

        mFragmentRegistrationBinding.driverQualificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRegistrationViewModel.setQualification(getActivity().getResources().getStringArray(R.array.qualification_driver)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mFragmentRegistrationBinding.showPasswordSwitcher.setOnCheckedChangeListener((v, isChecked) -> {
            TransformationMethod method = isChecked ? null : new PasswordTransformationMethod();
            mFragmentRegistrationBinding.passwordRegistrationEditText.setTransformationMethod(method);
            mFragmentRegistrationBinding.confirmPasswordRegistrationEditText.setTransformationMethod(method);
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mRegistrationViewModel.unsubscribe();
        super.onDestroy();
    }
}

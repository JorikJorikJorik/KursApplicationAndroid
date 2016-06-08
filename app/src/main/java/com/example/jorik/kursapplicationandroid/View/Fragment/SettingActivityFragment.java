package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.SettingViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentSettingBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class SettingActivityFragment extends Fragment {

    private SettingViewModel mSettingViewModel;
    private FragmentSettingBinding mSettingBinding;

    public static SettingActivityFragment newInstance() {
        SettingActivityFragment fragment = new SettingActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getString(R.string.setting_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mSettingViewModel = new SettingViewModel(getActivity());
        mSettingBinding = DataBindingUtil.bind(view);
        mSettingBinding.setTime(mSettingViewModel);
        return view;
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

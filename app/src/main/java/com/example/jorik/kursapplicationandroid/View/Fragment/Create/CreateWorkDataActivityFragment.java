package com.example.jorik.kursapplicationandroid.View.Fragment.Create;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateWorkDataActivity;
import com.example.jorik.kursapplicationandroid.View.Fragment.BaseFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.Create.CreateWorkViewModel;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.databinding.FragmentCreateWorkDataActivityBinding;

import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateWorkDataActivityFragment extends BaseFragment implements BaseViewModel.ResponseCallback {

    private final static  int DATE_CONSTANT = 1900;
    private CreateWorkViewModel mCreateWorkViewModel;
    private FragmentCreateWorkDataActivityBinding mFragmentCreateWorkDataActivityBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getString(R.string.create_work_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_work_data_activity, container, false);

        mFragmentCreateWorkDataActivityBinding = DataBindingUtil.bind(view);
        mCreateWorkViewModel = new CreateWorkViewModel(getActivity(), mFragmentCreateWorkDataActivityBinding.createWorkSwipe, CreateWorkDataActivityFragment.this);

        mFragmentCreateWorkDataActivityBinding.createWorkSwipe.setColorSchemeColors(Color.BLUE);
        mFragmentCreateWorkDataActivityBinding.createWorkRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentCreateWorkDataActivityBinding.createCalendar.setOnDateChangeListener((viewCalendar, year, month, day) -> {
            mCreateWorkViewModel.validationDateChoose(new Date(year - DATE_CONSTANT, month, day).getTime(), true);
        });

        mFragmentCreateWorkDataActivityBinding.setCreateWork(mCreateWorkViewModel);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCreateWorkViewModel.getAllDriverRequest();

    }

    @Override
    public void onDestroy() {
        mCreateWorkViewModel.unsubscriber();
        super.onDestroy();
    }

    @Override
    public void responseFromServer(Integer response) {
        if (response.equals(200)) {
            Toast.makeText(getActivity(), "New gas blank created", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        else Snackbar.make(mFragmentCreateWorkDataActivityBinding.workCoordinator, "Error response", Snackbar.LENGTH_SHORT)
                .setAction("Refresh", v -> mCreateWorkViewModel.createWorkList()).show();
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

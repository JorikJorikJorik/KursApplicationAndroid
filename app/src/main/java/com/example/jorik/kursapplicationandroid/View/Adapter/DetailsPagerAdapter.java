package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jorik.kursapplicationandroid.View.Fragment.DetailsPagerFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsViewModel;

import java.util.ArrayList;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private int chooseId;
    private int countPages;
    private ArrayList<String> photoList;
    private DetailsViewModel mViewModel;

    public DetailsPagerAdapter(FragmentManager manager, int countPages, ArrayList<String> list, int chooseId, DetailsViewModel viewModel){
        super(manager);
        this.countPages = countPages;
        this.chooseId = chooseId;
        mViewModel = viewModel;
        photoList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsPagerFragment.newInstance(photoList, chooseId, mViewModel);
    }

    @Override
    public int getCount() {
        return countPages;
    }

}

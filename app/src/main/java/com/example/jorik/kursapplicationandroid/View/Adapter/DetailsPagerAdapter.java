package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.View.Fragment.DetailsPagerFragment;

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private int chooseId;
    private int countPages;
    private KindDataBase mKindDataBase;

    public DetailsPagerAdapter(FragmentManager manager, int countPages, KindDataBase mKindDataBase, int chooseId){
        super(manager);
        this.countPages = countPages;
        this.mKindDataBase = mKindDataBase;
        this.chooseId = chooseId;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsPagerFragment.newInstance(mKindDataBase, position, chooseId);
    }

    @Override
    public int getCount() {
        return countPages;
    }

}

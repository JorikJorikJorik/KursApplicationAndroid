package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.POJO.DetailsModel;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DetailsPagerAdapter;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsViewModel extends BaseObservable {

    private static final int COUNT_PAGES_BUS = 3;
    private static final int COUNT_PAGES_OTHER = 1;

    private FragmentManager mFragmentManager;
    private DetailsModel mDetailsModel;
    private Context mContext;
    private int chooseId;

    public DetailsViewModel(Context mContext, KindDataBase mKindDataBase, FragmentManager mFragmentManager,int chooseId){
        mDetailsModel = new DetailsModel();
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.chooseId = chooseId;
        setKindDataBase(mKindDataBase);
    }

    @Bindable
    public KindDataBase getKindDataBase() {
        return mDetailsModel.getKindDataBase();
    }

    public void setKindDataBase(KindDataBase kindDataBase) {
        mDetailsModel.setKindDataBase(kindDataBase);
        notifyPropertyChanged(BR.kindDataBase);
    }

    @Bindable
    public int getCountPages() {
        return mDetailsModel.getCountPages();
    }

    public void setCountPages(int countPages) {
        mDetailsModel.setCountPages(countPages);
        notifyPropertyChanged(BR.countPages);
    }

    @Bindable
    public int getTitleImage() {
        return mDetailsModel.getTitleImage();
    }

    public void setTitleImage(int titleImage) {
        mDetailsModel.setTitleImage(titleImage);
        notifyPropertyChanged(BR.titleImage);
    }

    @Bindable
    public String getTitlePage() {
        return mDetailsModel.getTitlePage();
    }

    public void setTitlePage(String titlePage) {
        mDetailsModel.setTitlePage(titlePage);
        notifyPropertyChanged(BR.titlePage);
    }

    public void setCustomViewTabLayout(TabLayout tabLayout){

        String[] title = mDetailsModel.createTitle(mContext);
        int[] icon = mDetailsModel.createIcon();

        for(int i = 0; i < getCountPages(); i++) {
            tabLayout.getTabAt(i).setText(title[i]);
            tabLayout.getTabAt(i).setIcon(icon[i]);
        }

        setTitleImage(mDetailsModel.createIconTitle());
    }

    public FragmentPagerAdapter createAdapterViewPage(){
        setTitlePage(mDetailsModel.createPageTitle(mContext));
        setCountPages(getKindDataBase() == KindDataBase.BUS ? COUNT_PAGES_BUS : COUNT_PAGES_OTHER);
        return new DetailsPagerAdapter(mFragmentManager, getCountPages(), getKindDataBase(), chooseId);
    }



}

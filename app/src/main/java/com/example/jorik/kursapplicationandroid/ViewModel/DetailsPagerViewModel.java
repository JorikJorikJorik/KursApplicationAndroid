package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Model.POJO.DetailsPageModel;

import java.util.ArrayList;

/**
 * Created by jorik on 27.05.16.
 */

public class DetailsPagerViewModel extends BaseObservable implements DetailsViewModel.ViewPagerCallback {

    private Context mContext;
    private DetailsPageModel mDetailsPageModel;
    private ArrayList<String> photoList;

    public DetailsPagerViewModel(Context mContext, ArrayList<String> list) {
        this.mContext = mContext;
        photoList = list;
        mDetailsPageModel = new DetailsPageModel();
    }


    @Bindable
    public String getUrlPhoto() {
        return mDetailsPageModel.getUrlPhoto();
    }

    public void setUrlPhoto(String url) {
        mDetailsPageModel.setUrlPhoto(url);
        notifyPropertyChanged(BR.urlPhoto);
    }

    public void createPhoto(int chooseId) {
        setUrlPhoto(photoList.get(chooseId));
    }

    @Override
    public void setPosition(int position) {
        if (position < photoList.size())
            createPhoto(position);
    }


}

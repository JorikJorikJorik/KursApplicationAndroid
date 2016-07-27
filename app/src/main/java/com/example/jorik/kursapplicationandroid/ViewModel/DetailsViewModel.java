package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jorik.kursapplicationandroid.View.Adapter.DetailsPagerAdapter;
import com.example.jorik.kursapplicationandroid.View.Fragment.DetailsPagerFragment;

import java.util.ArrayList;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsViewModel extends BaseObservable implements Parcelable, DetailsPagerFragment.ViewModelCallback {

    private FragmentManager mFragmentManager;
    private Context mContext;
    private int chooseId;
    private ArrayList<String> photoList;
    private ViewPagerCallback mCallback;
    private DetailsPagerViewModel mDetailsPagerViewModel;


    public static final Creator<DetailsViewModel> CREATOR = new Creator<DetailsViewModel>() {
        @Override
        public DetailsViewModel createFromParcel(Parcel in) {
            return new DetailsViewModel(in);
        }

        @Override
        public DetailsViewModel[] newArray(int size) {
            return new DetailsViewModel[size];
        }
    };

    public DetailsViewModel(Context mContext, ArrayList<String> list, FragmentManager mFragmentManager, int chooseId) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.chooseId = chooseId;
        photoList = list;
    }

    protected DetailsViewModel(Parcel in) {
        chooseId = in.readInt();
        photoList = in.createStringArrayList();
    }

    public void changePage(int state) {
        if (mDetailsPagerViewModel != null) {
            mCallback = (ViewPagerCallback) mDetailsPagerViewModel;
            mCallback.setPosition(state);
        }
    }

    public FragmentPagerAdapter createAdapterViewPage() {
        return new DetailsPagerAdapter(mFragmentManager, photoList.size(), photoList, chooseId, DetailsViewModel.this);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chooseId);
        dest.writeStringList(photoList);
    }

    @Override
    public void getViewModel(DetailsPagerViewModel viewModel) {
        mDetailsPagerViewModel = viewModel;
    }

    public interface ViewPagerCallback {
        public void setPosition(int position);
    }


}

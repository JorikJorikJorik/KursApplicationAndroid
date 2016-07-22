package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DetailsPagerAdapter;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsModel {

    private Context mContext;
    private KindDataBase mKindDataBase;
    private int countPages;
    private String titleImage;
    private String titlePage;

    public DetailsModel(Context context) {
        mContext = context;
    }

    public KindDataBase getKindDataBase() {
        return mKindDataBase;
    }

    public void setKindDataBase(KindDataBase kindDataBase) {
        mKindDataBase = kindDataBase;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
    }

    public String[] createTitle(Context mContext){
        return mKindDataBase == KindDataBase.BUS ? mContext.getResources().getStringArray(R.array.details_title_bus) : mContext.getResources().getStringArray(R.array.details_title_driver);
    }

    public int[] createIcon(){

//        Resources resources = mContext.getResources();
//        TypedArray typedArray = resources.obtainTypedArray(mKindDataBase ==KindDataBase.BUS ? R.array.details_title_bus_icon : R.array.details_title_driver_icon);

        return mKindDataBase == KindDataBase.BUS ? new int[]{R.mipmap.white_details, R.mipmap.white_gas, R.mipmap.white_repair} : new int[]{R.mipmap.white_details};
    }

    public int createIconTitle(){
        return mKindDataBase == KindDataBase.BUS ? R.mipmap.bus_main : R.mipmap.driver_main;
    }

    public String createPageTitle(Context mContext){
        return  mKindDataBase == KindDataBase.BUS ?  mContext.getString(R.string.title_bus) : mContext.getString(R.string.title_driver);
    }

   
}

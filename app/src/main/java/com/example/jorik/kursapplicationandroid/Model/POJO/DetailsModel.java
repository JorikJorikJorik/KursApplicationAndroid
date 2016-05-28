package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.content.Context;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DetailsPagerAdapter;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsModel {

    private KindDataBase mKindDataBase;
    private int countPages;
    private int titleImage;
    private String titlePage;

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

    public int getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(int titleImage) {
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
        return mKindDataBase == KindDataBase.BUS ? new int[]{R.mipmap.white_details, R.mipmap.white_gas, R.mipmap.white_repair} : new int[]{R.mipmap.white_details};
    }

    public int createIconTitle(){
        return mKindDataBase == KindDataBase.BUS ? R.mipmap.bus_main : R.mipmap.driver_main;
    }

    public String createPageTitle(Context mContext){
        return  mKindDataBase == KindDataBase.BUS ?  mContext.getString(R.string.tiitle_bus) : mContext.getString(R.string.titile_driver);
    }

   
}

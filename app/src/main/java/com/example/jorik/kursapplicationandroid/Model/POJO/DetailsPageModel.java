package com.example.jorik.kursapplicationandroid.Model.POJO;

import android.support.v7.widget.RecyclerView;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.GasAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.RepairAdapter;

import java.sql.Driver;

/**
 * Created by jorik on 27.05.16.
 */

public class DetailsPageModel {

    private KindDataBase mKindDataBase;
    private boolean completed;
    private boolean progress;
    private String errorText;
    private RecyclerView.Adapter<?> mAdapter;



    public KindDataBase getKindDataBase() {
        return mKindDataBase;
    }

    public void setKindDataBase(KindDataBase kindDataBase) {
        mKindDataBase = kindDataBase;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public RecyclerView.Adapter<?> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        mAdapter = adapter;
    }

    public boolean getProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }
}

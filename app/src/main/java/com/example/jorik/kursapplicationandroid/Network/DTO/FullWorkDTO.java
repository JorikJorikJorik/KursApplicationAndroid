package com.example.jorik.kursapplicationandroid.Network.DTO;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jorik.kursapplicationandroid.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 01.06.16.
 */

public class FullWorkDTO extends BaseObservable{

    @SerializedName("WorkList")
    @Expose
    private WorkDTO mWorkDTO;

    @Expose
    @SerializedName("Driver")
    private DriverDTO mDriverDTO;

    @Expose
    @SerializedName("Bus")
    private BusDTO mBusDTO;

    @Bindable
    public WorkDTO getWorkDTO() {
        return mWorkDTO;
    }

    public void setWorkDTO(WorkDTO workDTO) {
        mWorkDTO = workDTO;
        notifyPropertyChanged(BR.workDTO);
    }

    @Bindable
    public DriverDTO getDriverDTO() {
        return mDriverDTO;
    }

    public void setDriverDTO(DriverDTO driverDTO) {
        mDriverDTO = driverDTO;
        notifyPropertyChanged(BR.driverDTO);
    }

    @Bindable
    public BusDTO getBusDTO() {
        return mBusDTO;
    }

    public void setBusDTO(BusDTO busDTO) {
        mBusDTO = busDTO;
        notifyPropertyChanged(BR.busDTO);
    }

}

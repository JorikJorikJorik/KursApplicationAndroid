package com.example.jorik.kursapplicationandroid.Network.DTO;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.jorik.kursapplicationandroid.BR;

/**
 * Created by jorik on 01.06.16.
 */

public class FullRepairDTO extends BaseObservable{

    @Expose
    @SerializedName("RepairBlank")
    private RepairDTO mRepairDTO;

    @Expose
    @SerializedName("Bus")
    private BusDTO mBusDTO;

    @Bindable
    public RepairDTO getRepairDTO() {
        return mRepairDTO;
    }

    public void setRepairDTO(RepairDTO repairDTO) {
        mRepairDTO = repairDTO;
        notifyPropertyChanged(BR.repairDTO);
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

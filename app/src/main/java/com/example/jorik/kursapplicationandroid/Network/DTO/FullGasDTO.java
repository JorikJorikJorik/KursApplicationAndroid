package com.example.jorik.kursapplicationandroid.Network.DTO;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.GasDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullGasDTO extends BaseObservable {

    @Expose
    @SerializedName("GasBlank")
    private GasDTO mGasDTO;

    @Expose
    @SerializedName("Bus")
    private BusDTO mBusDTO;

    @Bindable
    public GasDTO getGasDTO() {
        return mGasDTO;
    }

    public void setGasDTO(GasDTO gasDTO) {
        mGasDTO = gasDTO;
        notifyPropertyChanged(BR.gasDTO);
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

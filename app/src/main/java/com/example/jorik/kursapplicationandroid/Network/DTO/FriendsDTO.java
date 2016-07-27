package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jorik on 26.07.16.
 */

public class FriendsDTO {

    @SerializedName("data")
    @Expose
    private List<DataFriendsDTO> data;

    public List<DataFriendsDTO> getData() {
        return data;
    }

    public void setData(List<DataFriendsDTO> data) {
        this.data = data;
    }
}

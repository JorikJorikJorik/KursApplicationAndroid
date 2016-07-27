package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jorik on 27.07.16.
 */

public class DataAlbums {

    @SerializedName("data")
    @Expose
    private List<AlbumInfoDTO> data;

    public List<AlbumInfoDTO> getData() {
        return data;
    }

    public void setData(List<AlbumInfoDTO> data) {
        this.data = data;
    }
}

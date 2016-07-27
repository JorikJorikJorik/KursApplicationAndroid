package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jorik on 27.07.16.
 */

public class PhotosDTO {
    @SerializedName("data")
    @Expose
    private List<DataPhotosDTO> listDataPhotosDTO;

    public List<DataPhotosDTO> getListDataPhotosDTO() {
        return listDataPhotosDTO;
    }

    public void setListDataPhotosDTO(List<DataPhotosDTO> listDataPhotosDTO) {
        this.listDataPhotosDTO = listDataPhotosDTO;
    }
}

package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 26.07.16.
 */
public class PictureDTO {

    @SerializedName("data")
    @Expose
    private DataPictureDTO dataPictureDTO;

    public DataPictureDTO getDataPictureDTO() {
        return dataPictureDTO;
    }

    public void setDataPictureDTO(DataPictureDTO dataPictureDTO) {
        this.dataPictureDTO = dataPictureDTO;
    }
}

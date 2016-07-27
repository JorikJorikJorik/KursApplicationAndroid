package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 26.07.16.
 */
public class DataPictureDTO {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("is_silhouette")
    @Expose
    private Boolean isSilhouette;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSilhouette() {
        return isSilhouette;
    }

    public void setSilhouette(Boolean silhouette) {
        isSilhouette = silhouette;
    }
}

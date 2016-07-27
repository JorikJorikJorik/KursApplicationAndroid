package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 26.07.16.
 */
public class DataFriendsDTO {

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("middle_name")
    @Expose
    private String middle_name;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("picture")
    @Expose
    private PictureDTO picture;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PictureDTO getPicture() {
        return picture;
    }

    public void setPicture(PictureDTO picture) {
        this.picture = picture;
    }
}

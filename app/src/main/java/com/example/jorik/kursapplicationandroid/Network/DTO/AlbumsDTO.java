package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 27.07.16.
 */

public class AlbumsDTO {

    @SerializedName("albums")
    @Expose
    private DataAlbums albums;

    public DataAlbums getAlbums() {
        return albums;
    }

    public void setAlbums(DataAlbums albums) {
        this.albums = albums;
    }
}

package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jorik on 27.07.16.
 */

public class TaggableFriendsDTO {

    @SerializedName("taggable_friends")
    @Expose
    private FriendsDTO mFriendsDTO;

    public FriendsDTO getFriendsDTO() {
        return mFriendsDTO;
    }

    public void setFriendsDTO(FriendsDTO friendsDTO) {
        mFriendsDTO = friendsDTO;
    }
}

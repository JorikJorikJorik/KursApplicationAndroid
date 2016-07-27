package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jorik on 27.07.16.
 */

public class UserDataDTO {
    @SerializedName("data")
    @Expose
    private UserDTO mUserDTO;

    public UserDTO getUserDTO() {
        return mUserDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        mUserDTO = userDTO;
    }
}

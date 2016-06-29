package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountDriverDTO {

    @SerializedName("AccountModel")
    @Expose
    private AccountDTO mAccountDTO;

    @SerializedName("DriverModel")
    @Expose
    private DriverDTO mDriverDTO;

    public AccountDTO getAccountDTO() {
        return mAccountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        mAccountDTO = accountDTO;
    }

    public DriverDTO getDriverDTO() {
        return mDriverDTO;
    }

    public void setDriverDTO(DriverDTO driverDTO) {
        mDriverDTO = driverDTO;
    }
}

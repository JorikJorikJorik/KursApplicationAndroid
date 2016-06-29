package com.example.jorik.kursapplicationandroid.Network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountDispatcherDTO {
    @SerializedName("AccountModel")
    @Expose
    private AccountDTO mAccountDTO;

    public AccountDTO getAccountDTO() {
        return mAccountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        mAccountDTO = accountDTO;
    }
}

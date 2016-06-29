package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.AccountDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.AccountDispatcherDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.AccountDriverDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by jorik on 29.06.16.
 */

public interface AccountService {

    @POST("AccountUser/Driver")
    Observable<Integer> createDriverAccount(@Body AccountDriverDTO accountDriver);

    @POST("AccountUser/Dispatcher")
    Observable<Integer> createDispatcherAccount(@Body AccountDispatcherDTO accountDispatcher);

    @POST("SingIn")
    Observable<ArrayList<String>> createSignIn(@Body AccountDTO accountDTO);
}

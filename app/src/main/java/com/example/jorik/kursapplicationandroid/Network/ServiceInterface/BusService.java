package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;


import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by jorik on 18.05.16.
 */
public interface BusService {

    @GET("BusValues")
    Observable<List<BusDTO>> getBusList();
}

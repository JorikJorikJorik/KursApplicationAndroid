package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.GasDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.RepairDTO;


import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by jorik on 18.05.16.
 */
public interface BusService {

    @GET("BusValues")
    Observable<List<BusDTO>> getAllBuses();

    @GET("BusValues/{id}")
    Observable<BusDTO> getBus(@Path("id") int id);

    @POST("BusValues")
    Observable<Integer> createBus(@Body BusDTO bus);

    @DELETE("BusValues/{id}")
    Observable<Integer> deleteBus(@Path("id") int id);

    @GET("BusValues/Repair/{id}")
    Observable<List<RepairDTO>> getRepairListById(@Path("id") int id);

    @GET("BusValues/Gas/{id}")
    Observable<List<GasDTO>> getGasListById(@Path("id") int id);

}

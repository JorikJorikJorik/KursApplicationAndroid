package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

import retrofit.http.GET;

/**
 * Created by jorik on 20.05.16.
 */

public interface DriverService {

    @GET("DriverValues")
    Observable<List<DriverDTO>> getAllDrivers();

    @GET("DriverValues/{id}")
    Observable<DriverDTO> getDriver(@Path("id") int id);

    @POST("DriverValues")
    Observable<Integer> createDriver(@Body DriverDTO driver);

    @DELETE("DriverValues/{id}")
    Observable<Integer> deleteDriver(@Path("id") int id);
}

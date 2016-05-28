package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.GasDTO;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by jorik on 27.05.16.
 */

public interface GasService {

    @GET("GasList")
    Observable<List<GasDTO>> getAllGasList();

    @GET("GasList/{id}")
    Observable<GasDTO> getGasList(@Path("id") int id);

    @POST("GasList")
    Observable<Integer> createGasList(@Body GasDTO gasList);

    @DELETE("GasList/{id}")
    Observable<Integer> deleteGasList(@Path("id") int id);


}

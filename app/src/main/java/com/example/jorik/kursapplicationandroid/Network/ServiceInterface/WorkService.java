package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.FullWorkDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.WorkDTO;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by jorik on 01.06.16.
 */

public interface WorkService {

    @GET("WorkList")
    Observable<List<FullWorkDTO>> getAllWorkList();

    @GET("WorkList/{id}")
    Observable<FullWorkDTO> getWorkList(@Path("id") int id);

    @POST("WorkList")
    Observable<Integer> createWorkList(@Body WorkDTO mWorkDTO);

    @DELETE("WorkList/{id}")
    Observable<Integer> deleteWorkList(@Path("id") int id);

}

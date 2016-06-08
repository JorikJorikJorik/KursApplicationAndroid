package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.FullRepairDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.RepairDTO;

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

public interface RepairService {

    @GET("RepairList")
    Observable<List<RepairDTO>> getAllRepairList();

    @GET("RepairList/{id}")
    Observable<RepairDTO> getRepairList(@Path("id") int id);

    @POST("RepairList/{number}")
    Observable<Integer> createRepairList(@Body RepairDTO repairList, @Path("number") int number);

    @DELETE("RepairList/{id}")
    Observable<Integer> deleteRepairList(@Path("id") int id);

    @GET("RepairList/Full")
    Observable<List<FullRepairDTO>> getAllFullRepairList();

    @GET("RepairList/Full/{number}")
    Observable<List<FullRepairDTO>> getAllFullRepairListForUser(@Path("number") int number);
}

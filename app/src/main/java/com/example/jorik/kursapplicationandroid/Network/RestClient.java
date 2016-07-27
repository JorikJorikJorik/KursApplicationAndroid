package com.example.jorik.kursapplicationandroid.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by jorik on 16.05.16.
 */
public class RestClient {

    private static String baseUrl= "https://graph.facebook.com/";
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static <T> T getServiceInterface(Class<T> serviceInterfaceClass){

        Retrofit service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return service.create(serviceInterfaceClass);
    }
}

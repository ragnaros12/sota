package com.company.vika;

import java.lang.annotation.Retention;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static String Path = "https://www.profsota.ru/", Path1 = "https://jsonkeeper.com/";
    private static Retrofit retrofit, retrofit1;
    public static NetworkService Instance;

    public NetworkService() {
        retrofit = new Retrofit.Builder().baseUrl(Path).addConverterFactory(GsonConverterFactory.create()).build();
        retrofit1 = new Retrofit.Builder().baseUrl(Path1).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static NetworkService getInstance() {
        if(Instance == null){
            Instance = new NetworkService();
        }
        return Instance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

    public ApiJson getApiJson(){
        return retrofit1.create(ApiJson.class);
    }
}

package com.company.vika;

import android.content.Intent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("/abiturientam/professionalnye-obrazovatelnye-programmy-spo")
    Call<ResponseBody> getAbiData();

    @POST("/index.php")
    Call<ResponseBody> getNews(@Query("start") Integer start);

    @POST("/kontakty")
    Call<ResponseBody> getContacts();


    @POST("/{data}")
    Call<ResponseBody> getNews(@Path("data") String path);
}

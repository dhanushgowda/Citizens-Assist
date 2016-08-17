package com.tw.awayday.citizensassist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("/api/user")
//    @POST("/")
    Call<Image> saveImage(@Body Image image);

    @GET("/api/user")
    Call<List<Image>> getImage();
}

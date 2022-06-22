package com.pinkoochintantest.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    String BASE_URL = "https://api.pioustair.in/";

    @POST("api/Customer/login")
    Call<JsonObject> login(@Body HashMap<String,Object> map);

    @GET("api/Category/all")
    Call<JsonArray> getCategory(@Header("Authorization") String token);

    @GET("api/Product/all")
    Call<JsonArray> getProduct(@Header("Authorization") String token);

}

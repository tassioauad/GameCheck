package com.tassioauad.gamecatalog.model.api.resource;

import com.tassioauad.gamecatalog.model.entity.Game;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GameResource {

    @GET("/games/?api_key={apikey}&format=json&limit={count}&sort=original_release_date:desc")
    Call<List<Game>> searchLasts(@Path("apikey") String apiKey, @Path("count") Integer count);

    @GET("/games/?api_key={apikey}&format=json&sort=original_release_date:desc&name={name}")
    Call<List<Game>> searchByName(@Path("apikey") String apiKey, @Path("name") String name);

    @GET("/games/?api_key={apikey}&format=json&sort=original_release_date:desc&platforms={platformId}")
    Call<List<Game>> searchByPlatform(@Path("apikey") String apiKey, @Path("platformId") Long platformId);

}

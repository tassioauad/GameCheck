package com.tassioauad.gamecatalog.model.api.resource;

import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface PlatformResource {

    @GET("/platforms/?api_key={apikey}&format=json&limit={count}&sort=release_date:desc")
    Call<List<Platform>> searchLasts(@Path("apikey") String apiKey, @Path("count") Integer count);

    @GET("/platforms/?api_key={apikey}&format=json&sort=release_date:desc&name={name}")
    Call<List<Platform>> searchByName(@Path("apikey") String apiKey,@Path("name") String name);

}

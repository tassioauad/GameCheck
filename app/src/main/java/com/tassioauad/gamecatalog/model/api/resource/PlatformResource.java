package com.tassioauad.gamecatalog.model.api.resource;

import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PlatformResource {

    @GET("/api/platforms/")
    Call<List<Platform>> searchLasts(@Query("api_key") String apiKey, @Query("count") Integer count,
                                     @Query("sort") String sort, @Query("format") String format);

    @GET("/api/platforms/")
    Call<List<Platform>> searchByName(@Query("api_key") String apiKey, @Query("name") String name,
                                      @Query("sort") String sort, @Query("format") String format);

}

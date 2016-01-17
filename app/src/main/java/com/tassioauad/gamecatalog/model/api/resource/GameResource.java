package com.tassioauad.gamecatalog.model.api.resource;

import com.tassioauad.gamecatalog.model.entity.Game;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GameResource {

    @GET("/api/games/")
    Call<List<Game>> searchLasts(@Query("api_key") String apiKey, @Query("limit") Integer count,
                                 @Query("sort") String sort, @Query("format") String format);

    @GET("/api/games/")
    Call<List<Game>> searchByName(@Query("api_key") String apiKey, @Query("name") String name,
                                  @Query("sort") String sort, @Query("format") String format);

    @GET("/api/games/")
    Call<List<Game>> searchByPlatform(@Query("api_key") String apiKey, @Query("platforms") Long platformId,
                                      @Query("sort") String sort, @Query("format") String format);

}

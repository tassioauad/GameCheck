package com.tassioauad.gamecheck.model.api.asynctask.impl;

import android.content.Context;

import com.tassioauad.gamecheck.model.api.asynctask.AsyncTaskResult;
import com.tassioauad.gamecheck.model.api.asynctask.GenericAsyncTask;
import com.tassioauad.gamecheck.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.gamecheck.model.api.resource.GameResource;
import com.tassioauad.gamecheck.model.entity.Game;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class GameSearchLastsAsyncTask extends GenericAsyncTask<Integer, Void, List<Game>> {

    private GameResource gameResource;
    private final String sort = "original_release_date:desc";
    private final String format = "json";

    public GameSearchLastsAsyncTask(Context context, GameResource gameResource) {
        super(context);
        this.gameResource = gameResource;
    }

    @Override
    protected AsyncTaskResult<List<Game>> doInBackground(Integer... params) {

        try {
            Call<List<Game>> listCall = gameResource.searchLasts(getApiKey(), params[0], sort, format);
            Response<List<Game>> response = listCall.execute();
            switch (response.code()) {
                case HTTP_OK:
                    return new AsyncTaskResult<>(response.body());
                default:
                    return new AsyncTaskResult<>(new BadRequestException());
            }
        } catch (IOException e) {
            return new AsyncTaskResult<>(new BadRequestException());
        }
    }
}

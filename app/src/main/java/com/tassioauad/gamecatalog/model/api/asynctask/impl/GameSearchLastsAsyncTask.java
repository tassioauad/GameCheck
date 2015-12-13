package com.tassioauad.gamecatalog.model.api.asynctask.impl;

import android.content.Context;

import com.tassioauad.gamecatalog.model.api.asynctask.AsyncTaskResult;
import com.tassioauad.gamecatalog.model.api.asynctask.GenericAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.gamecatalog.model.api.resource.GameResource;
import com.tassioauad.gamecatalog.model.entity.Game;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

public class GameSearchLastsAsyncTask extends GenericAsyncTask<Void, Void, List<Game>> {

    private GameResource gameResource;
    private final int NUMBER_OF_GAMES = 20;

    public GameSearchLastsAsyncTask(Context context, GameResource gameResource) {
        super(context);
        this.gameResource = gameResource;
    }

    @Override
    protected AsyncTaskResult<List<Game>> doInBackground(Void... params) {

        try {
            Response<List<Game>> response = gameResource.searchLasts(getApiKey(), NUMBER_OF_GAMES).execute();
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

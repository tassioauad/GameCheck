package com.tassioauad.gamecatalog.model.api.asynctask.impl;

import android.content.Context;

import com.tassioauad.gamecatalog.model.api.asynctask.AsyncTaskResult;
import com.tassioauad.gamecatalog.model.api.asynctask.GenericAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.gamecatalog.model.api.resource.PlatformResource;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class PlatformSearchLastsAsyncTask extends GenericAsyncTask<Integer, Void, List<Platform>> {

    private PlatformResource platformResource;
    private final String sort = "release_date:desc";
    private final String format = "json";

    public PlatformSearchLastsAsyncTask(Context context, PlatformResource platformResource) {
        super(context);
        this.platformResource = platformResource;
    }

    @Override
    protected AsyncTaskResult<List<Platform>> doInBackground(Integer... params) {

        try {
            Response<List<Platform>> response = platformResource.searchLasts(getApiKey(), params[0], sort, format).execute();
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

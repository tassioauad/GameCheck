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

public class PlatformSearchLastsAsyncTask extends GenericAsyncTask<Void, Void, List<Platform>> {

    private PlatformResource platformResource;
    private final int NUMBER_OF_PLATFORM = 20;

    public PlatformSearchLastsAsyncTask(Context context, PlatformResource platformResource) {
        super(context);
        this.platformResource = platformResource;
    }

    @Override
    protected AsyncTaskResult<List<Platform>> doInBackground(Void... params) {

        try {
            Response<List<Platform>> response = platformResource.searchLasts(getApiKey(), NUMBER_OF_PLATFORM).execute();
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

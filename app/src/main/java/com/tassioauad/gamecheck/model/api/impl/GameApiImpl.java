package com.tassioauad.gamecheck.model.api.impl;


import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.GenericApi;
import com.tassioauad.gamecheck.model.api.asynctask.impl.GameSearchByNameAsyncTask;
import com.tassioauad.gamecheck.model.api.asynctask.impl.GameSearchByPlatformAsyncTask;
import com.tassioauad.gamecheck.model.api.asynctask.impl.GameSearchLastsAsyncTask;
import com.tassioauad.gamecheck.model.api.resource.GameResource;
import com.tassioauad.gamecheck.model.entity.Platform;

public class GameApiImpl extends GenericApi implements GameApi {

    private GameSearchLastsAsyncTask gameSearchLastsAsyncTask;
    private GameSearchByNameAsyncTask gameSearchByNameAsyncTask;
    private GameSearchByPlatformAsyncTask gameSearchByPlatformAsyncTask;
    private GameResource gameResource;

    public GameApiImpl(Context context, GameResource gameResource) {
        super(context);
        this.gameResource = gameResource;
    }

    @Override
    public void searchLasts(Integer count) {
        verifyServiceResultListener();
        gameSearchLastsAsyncTask = new GameSearchLastsAsyncTask(getContext(), gameResource);
        gameSearchLastsAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchLastsAsyncTask.execute(count);
    }

    @Override
    public void searchByName(String name) {
        verifyServiceResultListener();
        gameSearchByNameAsyncTask = new GameSearchByNameAsyncTask(getContext(), gameResource);
        gameSearchByNameAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchByNameAsyncTask.execute(name);
    }

    @Override
    public void searchByPlatform(Platform platform) {
        verifyServiceResultListener();
        gameSearchByPlatformAsyncTask = new GameSearchByPlatformAsyncTask(getContext(), gameResource);
        gameSearchByPlatformAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchByPlatformAsyncTask.execute(platform);
    }

    @Override
    public void cancelAllService() {
        if(gameSearchLastsAsyncTask != null && gameSearchLastsAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            gameSearchLastsAsyncTask.cancel(true);
        }
        if(gameSearchByNameAsyncTask != null && gameSearchByNameAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            gameSearchByNameAsyncTask.cancel(true);
        }
        if(gameSearchByPlatformAsyncTask != null && gameSearchByPlatformAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            gameSearchByPlatformAsyncTask.cancel(true);
        }
    }
}

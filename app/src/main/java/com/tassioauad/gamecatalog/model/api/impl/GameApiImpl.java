package com.tassioauad.gamecatalog.model.api.impl;


import android.content.Context;

import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.model.api.GenericApi;
import com.tassioauad.gamecatalog.model.api.asynctask.GenericAsyncTask;
import com.tassioauad.gamecatalog.model.entity.Platform;

public class GameApiImpl extends GenericApi implements GameApi {

    private GenericAsyncTask gameSearchLastsAsyncTask;
    private GenericAsyncTask gameSearchByNameAsyncTask;
    private GenericAsyncTask gameSearchByPlatformAsyncTask;

    public GameApiImpl(Context context, GenericAsyncTask gameSearchLastsAsyncTask, GenericAsyncTask gameSearchByNameAsyncTask, GenericAsyncTask gameSearchByPlatformAsyncTask) {
        super(context);
        this.gameSearchLastsAsyncTask = gameSearchLastsAsyncTask;
        this.gameSearchByNameAsyncTask = gameSearchByNameAsyncTask;
        this.gameSearchByPlatformAsyncTask = gameSearchByPlatformAsyncTask;
    }

    @Override
    public void searchLasts(Integer count) {
        verifyServiceResultListener();
        gameSearchLastsAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchLastsAsyncTask.execute();
    }

    @Override
    public void searchByName(String name) {
        verifyServiceResultListener();
        gameSearchByNameAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchByNameAsyncTask.execute(name);
    }

    @Override
    public void searchByPlatform(Platform platform) {
        verifyServiceResultListener();
        gameSearchByPlatformAsyncTask.setApiResultListener(getServiceResultListener());
        gameSearchByPlatformAsyncTask.execute(platform);
    }
}

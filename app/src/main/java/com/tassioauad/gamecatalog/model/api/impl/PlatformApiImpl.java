package com.tassioauad.gamecatalog.model.api.impl;

import android.content.Context;

import com.tassioauad.gamecatalog.model.api.GenericApi;
import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.PlatformSearchByNameAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.PlatformSearchLastsAsyncTask;

public class PlatformApiImpl extends GenericApi implements PlatformApi {

    private PlatformSearchByNameAsyncTask platformSearchByNameAsyncTask;
    private PlatformSearchLastsAsyncTask platformSearchLastsAsyncTask;

    public PlatformApiImpl(Context context, PlatformSearchByNameAsyncTask platformSearchByNameAsyncTask, PlatformSearchLastsAsyncTask platformSearchLastsAsyncTask) {
        super(context);
        this.platformSearchByNameAsyncTask = platformSearchByNameAsyncTask;
        this.platformSearchLastsAsyncTask = platformSearchLastsAsyncTask;
    }

    @Override
    public void searchLasts(Integer count) {
        verifyServiceResultListener();
        platformSearchLastsAsyncTask.setApiResultListener(getServiceResultListener());
        platformSearchLastsAsyncTask.execute();
    }

    @Override
    public void searchByName(String name) {
        verifyServiceResultListener();
        platformSearchByNameAsyncTask.setApiResultListener(getServiceResultListener());
        platformSearchByNameAsyncTask.execute(name);
    }
}

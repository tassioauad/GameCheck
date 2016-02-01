package com.tassioauad.gamecheck.model.api.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.gamecheck.model.api.GenericApi;
import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.asynctask.impl.PlatformSearchByNameAsyncTask;
import com.tassioauad.gamecheck.model.api.asynctask.impl.PlatformSearchLastsAsyncTask;
import com.tassioauad.gamecheck.model.api.resource.PlatformResource;

public class PlatformApiImpl extends GenericApi implements PlatformApi {

    private PlatformSearchByNameAsyncTask platformSearchByNameAsyncTask;
    private PlatformSearchLastsAsyncTask platformSearchLastsAsyncTask;
    private PlatformResource platformResource;

    public PlatformApiImpl(Context context, PlatformResource platformResource) {
        super(context);
        this.platformResource = platformResource;
    }

    @Override
    public void searchLasts(Integer count) {
        verifyServiceResultListener();
        platformSearchLastsAsyncTask =
                new PlatformSearchLastsAsyncTask(getContext(), platformResource);
        platformSearchLastsAsyncTask.setApiResultListener(getServiceResultListener());
        platformSearchLastsAsyncTask.execute(count);
    }

    @Override
    public void searchByName(String name) {
        verifyServiceResultListener();
        platformSearchByNameAsyncTask =
                new PlatformSearchByNameAsyncTask(getContext(), platformResource);
        platformSearchByNameAsyncTask.setApiResultListener(getServiceResultListener());
        platformSearchByNameAsyncTask.execute(name);
    }

    @Override
    public void cancelAllService() {
        if(platformSearchByNameAsyncTask != null && platformSearchByNameAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            platformSearchByNameAsyncTask.cancel(true);
        }
        if(platformSearchLastsAsyncTask != null && platformSearchLastsAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            platformSearchLastsAsyncTask.cancel(true);
        }
    }
}

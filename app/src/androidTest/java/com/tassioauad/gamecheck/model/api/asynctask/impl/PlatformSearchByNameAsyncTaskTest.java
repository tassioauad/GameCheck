package com.tassioauad.gamecheck.model.api.asynctask.impl;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.api.resource.PlatformResource;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PlatformSearchByNameAsyncTaskTest extends AndroidTestCase {

    PlatformResource platformResource;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.giantbombapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        platformResource = retrofit.create(PlatformResource.class);
    }

    public void testDoInBackground() throws Exception {

        PlatformSearchByNameAsyncTask platformSearchByNameAsyncTask =
                new PlatformSearchByNameAsyncTask(getContext(), platformResource);

        final CountDownLatch signal = new CountDownLatch(1);
        platformSearchByNameAsyncTask.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Platform> platformList = (List<Platform>) object;
                assertTrue(platformList.size() > 0);
                signal.countDown();
            }

            @Override
            public void onException(Exception exception) {
                fail(exception.getMessage());
                signal.countDown();
            }
        });

        platformSearchByNameAsyncTask.execute(PlatformBuilder.aPlatform().build().getName());
        signal.await();

    }
}
package com.tassioauad.gamecheck.model.api.asynctask.impl;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.api.resource.GameResource;
import com.tassioauad.gamecheck.model.entity.Game;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class GameSearchByNameAsyncTaskTest extends AndroidTestCase {

    GameResource gameResource;

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

        gameResource = retrofit.create(GameResource.class);
    }

    public void testDoInBackground() throws Exception {

        final CountDownLatch signal = new CountDownLatch(1);

        GameSearchByNameAsyncTask gameSearchByNameAsyncTask =
                new GameSearchByNameAsyncTask(getContext(), gameResource);

        gameSearchByNameAsyncTask.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Game> gameList = (List<Game>) object;
                assertTrue(gameList.size() > 0);
                signal.countDown();
            }

            @Override
            public void onException(Exception exception) {
                fail(exception.getMessage());
                signal.countDown();
            }
        });

        gameSearchByNameAsyncTask.execute(GameBuilder.aGame().build().getName());
        signal.await();

    }
}
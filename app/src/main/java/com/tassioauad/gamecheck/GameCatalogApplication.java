package com.tassioauad.gamecheck;

import android.app.Application;

import com.tassioauad.gamecheck.dagger.ApiModule;
import com.tassioauad.gamecheck.dagger.AppModule;

import dagger.ObjectGraph;

public class GameCatalogApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(
                new Object[]{
                        new AppModule(GameCatalogApplication.this),
                        new ApiModule(),
                }
        );
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

}

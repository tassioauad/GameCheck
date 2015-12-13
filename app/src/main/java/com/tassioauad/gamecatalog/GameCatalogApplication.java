package com.tassioauad.gamecatalog;

import android.app.Application;

import com.tassioauad.gamecatalog.dagger.ApiModule;
import com.tassioauad.gamecatalog.dagger.AppModule;

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

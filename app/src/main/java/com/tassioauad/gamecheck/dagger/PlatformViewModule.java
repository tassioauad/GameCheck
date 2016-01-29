package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.database.PlatformRatingDao;
import com.tassioauad.gamecheck.presenter.PlatformPresenter;
import com.tassioauad.gamecheck.view.PlatformView;
import com.tassioauad.gamecheck.view.activity.PlatformActivity;

import dagger.Module;
import dagger.Provides;

@Module(library =  true, injects = PlatformActivity.class, includes = {AppModule.class, ApiModule.class, DaoModule.class})
public class PlatformViewModule {

    private PlatformView view;

    public PlatformViewModule(PlatformView view) {
        this.view = view;
    }

    @Provides
    public PlatformPresenter providePlatformPresenter(GameApi gameApi, PlatformRatingDao platformRatingDao) {
        return new PlatformPresenter(view, gameApi, platformRatingDao);
    }
}

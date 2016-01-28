package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.model.database.PlatformRatingDao;
import com.tassioauad.gamecatalog.presenter.PlatformPresenter;
import com.tassioauad.gamecatalog.view.PlatformView;
import com.tassioauad.gamecatalog.view.activity.PlatformActivity;

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

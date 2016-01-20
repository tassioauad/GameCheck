package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.presenter.HomePresenter;
import com.tassioauad.gamecatalog.view.HomeView;
import com.tassioauad.gamecatalog.view.activity.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module(injects = HomeActivity.class, includes = {ApiModule.class}, library = true)
public class HomeViewModule {

    private HomeView view;

    public HomeViewModule(HomeView view) {
        this.view = view;
    }

    @Provides
    public HomePresenter provideHomePresenter() {
        return new HomePresenter(view);
    }
}

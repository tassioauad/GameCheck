package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.presenter.HomePresenter;
import com.tassioauad.gamecheck.view.HomeView;
import com.tassioauad.gamecheck.view.activity.HomeActivity;

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

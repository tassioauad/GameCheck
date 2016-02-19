package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.presenter.SearchPlatformPresenter;
import com.tassioauad.gamecheck.view.SearchPlatformView;
import com.tassioauad.gamecheck.view.activity.SearchPlatformActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = SearchPlatformActivity.class, includes = {AppModule.class, ApiModule.class})
public class SearchPlatformViewModule {

    private SearchPlatformView view;

    public SearchPlatformViewModule(SearchPlatformView view) {
        this.view = view;
    }

    @Provides
    public SearchPlatformPresenter provideSearchPlatformPresenter(PlatformApi platformApi) {
        return new SearchPlatformPresenter(view, platformApi);
    }
}

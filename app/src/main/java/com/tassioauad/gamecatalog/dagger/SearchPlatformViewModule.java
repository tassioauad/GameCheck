package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.presenter.SearchPlatformPresenter;
import com.tassioauad.gamecatalog.view.SearchPlatformView;
import com.tassioauad.gamecatalog.view.activity.SearchPlatformActivity;

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

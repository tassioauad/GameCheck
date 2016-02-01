package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.presenter.SearchGamePresenter;
import com.tassioauad.gamecheck.view.SearchGameView;
import com.tassioauad.gamecheck.view.activity.SearchGameActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = SearchGameActivity.class, includes = {AppModule.class, ApiModule.class})
public class SearchGameViewModule {

    private SearchGameView view;

    public SearchGameViewModule(SearchGameView view) {
        this.view = view;
    }

    @Provides
    public SearchGamePresenter provideSearchGamePresenter(GameApi gameApi) {
        return new SearchGamePresenter(view, gameApi);
    }
}

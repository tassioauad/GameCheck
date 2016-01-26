package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.presenter.SearchGamePresenter;
import com.tassioauad.gamecatalog.view.SearchGameView;
import com.tassioauad.gamecatalog.view.activity.SearchGameActivity;

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

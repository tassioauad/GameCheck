package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.presenter.LastsGamePresenter;
import com.tassioauad.gamecatalog.view.LastsGameView;
import com.tassioauad.gamecatalog.view.fragment.LastsGameFragment;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class, library = true, injects = LastsGameFragment.class)
public class LastsGameViewModule {

    private LastsGameView view;

    public LastsGameViewModule(LastsGameView view) {
        this.view = view;
    }

    @Provides
    public LastsGamePresenter provideLastsGamePresenter(GameApi gameApi) {
        return new LastsGamePresenter(view, gameApi);
    }
}

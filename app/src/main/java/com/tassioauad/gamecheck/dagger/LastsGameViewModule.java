package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.presenter.LastsGamePresenter;
import com.tassioauad.gamecheck.view.LastsGameView;
import com.tassioauad.gamecheck.view.fragment.LastsGameFragment;

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

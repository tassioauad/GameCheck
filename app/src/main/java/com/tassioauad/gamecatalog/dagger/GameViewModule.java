package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.presenter.GamePresenter;
import com.tassioauad.gamecatalog.view.GameView;
import com.tassioauad.gamecatalog.view.activity.GameActivity;

import dagger.Module;
import dagger.Provides;

@Module(library =  true, injects = GameActivity.class, includes = {AppModule.class, ApiModule.class})
public class GameViewModule {

    private GameView view;

    public GameViewModule(GameView view) {
        this.view = view;
    }

    @Provides
    public GamePresenter provideGamePresenter() {
        return new GamePresenter(view);
    }
}

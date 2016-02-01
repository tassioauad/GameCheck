package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.database.GameRatingDao;
import com.tassioauad.gamecheck.presenter.GamePresenter;
import com.tassioauad.gamecheck.view.GameView;
import com.tassioauad.gamecheck.view.activity.GameActivity;

import dagger.Module;
import dagger.Provides;

@Module(library =  true, injects = GameActivity.class, includes = {AppModule.class, ApiModule.class, DaoModule.class})
public class GameViewModule {

    private GameView view;

    public GameViewModule(GameView view) {
        this.view = view;
    }

    @Provides
    public GamePresenter provideGamePresenter(GameRatingDao gameRatingDao) {
        return new GamePresenter(view, gameRatingDao);
    }
}

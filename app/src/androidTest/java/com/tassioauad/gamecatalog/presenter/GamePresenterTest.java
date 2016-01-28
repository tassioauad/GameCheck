package com.tassioauad.gamecatalog.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecatalog.entity.GameBuilder;
import com.tassioauad.gamecatalog.entity.PlatformBuilder;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.view.GameView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GamePresenterTest extends AndroidTestCase {

    GamePresenter presenter;
    GameView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(GameView.class);
        presenter = new GamePresenter(view, gameRatingDao);
    }

    public void testInit() {
        Game game = GameBuilder.aGame().withPlatform(PlatformBuilder.aPlatform().build()).build();

        presenter.init(game);

        verify(view, times(1)).showGame(game);
        verify(view, times(1)).showPlataforms(game.getPlatforms());
    }

}
package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.database.GameRatingDao;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.GameView;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GamePresenterTest extends AndroidTestCase {

    GamePresenter presenter;
    GameView view;
    GameRatingDao gameRatingDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(GameView.class);
        gameRatingDao = mock(GameRatingDao.class);
        presenter = new GamePresenter(view, gameRatingDao);
    }

    public void testInit_WithPlatform() {
        Float rating = 2f;
        Game game = GameBuilder.aGame().withPlatform(PlatformBuilder.aPlatform().build()).build();
        when(gameRatingDao.getRatingOf(any(Game.class))).thenReturn(rating);

        presenter.init(game);

        verify(view, times(1)).showGame(game);
        verify(view, times(1)).showRating(rating);
        verify(gameRatingDao, times(1)).getRatingOf(game);
        verify(view, times(1)).showPlataforms(game.getPlatforms());
        verify(view, never()).warnNoPlatform();
    }

    public void testInit_WithoutPlatform() {
        Float rating = 2f;
        Game game = GameBuilder.aGame().build();
        when(gameRatingDao.getRatingOf(any(Game.class))).thenReturn(rating);

        presenter.init(game);

        verify(view, times(1)).showGame(game);
        verify(view, times(1)).showRating(rating);
        verify(gameRatingDao, times(1)).getRatingOf(game);
        verify(view, never()).showPlataforms(game.getPlatforms());
        verify(view, times(1)).warnNoPlatform();
    }

    public void testInit_NoRating() {
        Game game = GameBuilder.aGame().build();
        when(gameRatingDao.getRatingOf(any(Game.class))).thenReturn(null);

        presenter.init(game);

        verify(view, times(1)).showGame(game);
        verify(view, never()).showRating(anyFloat());
        verify(gameRatingDao, times(1)).getRatingOf(game);
        verify(view, never()).showPlataforms(game.getPlatforms());
        verify(view, times(1)).warnNoPlatform();
    }

    public void testSetRating_RatingNull() {
        float rating = 1f;

        presenter.setRating(rating);

        verify(gameRatingDao, times(1)).insert(null, rating);
        verify(gameRatingDao, never()).update(any(Game.class), anyFloat());
    }

    public void testSetRating_RatingNotNull() {
        float firstRating = 1f;
        float secondRating = 2f;
        Game game = GameBuilder.aGame().build();
        when(gameRatingDao.getRatingOf(any(Game.class))).thenReturn(firstRating);

        presenter.init(game);
        presenter.setRating(secondRating);

        verify(gameRatingDao, never()).insert(game, secondRating);
        verify(gameRatingDao, times(1)).update(game, secondRating);
    }
}
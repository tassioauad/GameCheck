package com.tassioauad.gamecheck.presenter;

import com.tassioauad.gamecheck.model.database.GameRatingDao;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.GameView;

public class GamePresenter {

    private GameView view;
    private Game game;
    private GameRatingDao gameRatingDao;
    private Float rating;

    public GamePresenter(GameView view, GameRatingDao gameRatingDao) {
        this.view = view;
        this.gameRatingDao = gameRatingDao;
    }

    public void init(Game game) {
        this.game = game;
        rating = gameRatingDao.getRatingOf(game);
        if(game.getPlatforms() != null && game.getPlatforms().size() > 0) {
            view.showPlataforms(game.getPlatforms());
        } else {
            view.warnNoPlatform();
        }
        view.showGame(game);
        if(rating != null) {
            view.showRating(rating);
        }
    }

    public void setRating(float rating) {
        if(this.rating == null) {
            gameRatingDao.insert(game, rating);
        } else {
            gameRatingDao.update(game, rating);
        }
        this.rating = rating;
    }


}

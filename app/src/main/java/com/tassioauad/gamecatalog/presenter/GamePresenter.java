package com.tassioauad.gamecatalog.presenter;

import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.view.GameView;

public class GamePresenter {

    private GameView view;
    private Game game;

    public GamePresenter(GameView view) {
        this.view = view;
    }

    public void init(Game game) {
        this.game = game;
        view.showPlataforms(game.getPlatforms());
        view.showGame(game);

    }


}

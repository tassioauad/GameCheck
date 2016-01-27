package com.tassioauad.gamecatalog.view;

import com.tassioauad.gamecatalog.model.entity.Game;

import java.util.List;

public interface SearchGameView {
    void warnFailuredToListGame();

    void showGames(List<Game> gameList);

    void warnAnyGameFound();

    void showLoading();

    void dismissLoading();
}

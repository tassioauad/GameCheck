package com.tassioauad.gamecheck.view;

import com.tassioauad.gamecheck.model.entity.Game;

import java.util.List;

public interface SearchGameView {
    void warnFailuredToListGame();

    void showGames(List<Game> gameList);

    void warnAnyGameFound();

    void showLoading();

    void dismissLoading();
}

package com.tassioauad.gamecheck.view;


import com.tassioauad.gamecheck.model.entity.Game;

import java.util.List;

public interface LastsGameView {
    void showLoading();

    void dismissLoading();

    void warnCouldNotLoadGames();

    void showGames(List<Game> gameList);
}

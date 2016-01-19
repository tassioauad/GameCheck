package com.tassioauad.gamecatalog.view;


import com.tassioauad.gamecatalog.model.entity.Game;

import java.util.List;

public interface LastsGameView {
    void showLoading();

    void dismissLoading();

    void warnCouldNotLoadGames();

    void showGames(List<Game> gameList);
}

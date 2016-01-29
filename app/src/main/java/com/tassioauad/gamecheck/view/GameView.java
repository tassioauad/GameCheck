package com.tassioauad.gamecheck.view;

import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;

public interface GameView {
    void showPlataforms(List<Platform> platforms);

    void showGame(Game game);

    void warnNoPlatform();

    void showRating(float rating);
}

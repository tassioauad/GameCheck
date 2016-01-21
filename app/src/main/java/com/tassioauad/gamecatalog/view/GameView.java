package com.tassioauad.gamecatalog.view;

import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.Date;
import java.util.List;

public interface GameView {
    void showPlataforms(List<Platform> platforms);

    void showGame(Game game);
}

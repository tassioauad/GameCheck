package com.tassioauad.gamecatalog.view;

import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

public interface PlatformView {
    void showGames(List<Game> gameList);

    void showPlatform(Platform platform);

    void warnNoGame();

    void warnFailureToListGames();

    void showLoadingMessage();

    void hideLoadingMessage();
}

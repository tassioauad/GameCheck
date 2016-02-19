package com.tassioauad.gamecheck.view;

import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;

public interface PlatformView {
    void showGames(List<Game> gameList);

    void showPlatform(Platform platform);

    void warnNoGame();

    void warnFailureToListGames();

    void showLoading();

    void dismissLoading();

    void showRating(Float rating);
}

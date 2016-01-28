package com.tassioauad.gamecatalog.model.database;

import com.tassioauad.gamecatalog.model.entity.Game;

public interface GameRatingDao {
    boolean insert(Game game, float rating);

    boolean update(Game game, float rating);

    Float getRatingOf(Game game);
}

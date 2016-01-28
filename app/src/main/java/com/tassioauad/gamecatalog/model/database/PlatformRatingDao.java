package com.tassioauad.gamecatalog.model.database;

import com.tassioauad.gamecatalog.model.entity.Platform;

public interface PlatformRatingDao {
    boolean insert(Platform platform, float rating);

    boolean update(Platform platform, float rating);

    Float getRatingOf(Platform platform);
}

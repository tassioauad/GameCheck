package com.tassioauad.gamecheck.model.database;

import com.tassioauad.gamecheck.model.entity.Platform;

public interface PlatformRatingDao {
    boolean insert(Platform platform, float rating);

    boolean update(Platform platform, float rating);

    Float getRatingOf(Platform platform);
}

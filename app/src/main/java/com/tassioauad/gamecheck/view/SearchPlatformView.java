package com.tassioauad.gamecheck.view;

import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;

public interface SearchPlatformView {
    void warnFailuredToListPlatforms();

    void showPlatform(List<Platform> platformList);

    void warnAnyPlatformFound();

    void showLoading();

    void dismissLoading();
}

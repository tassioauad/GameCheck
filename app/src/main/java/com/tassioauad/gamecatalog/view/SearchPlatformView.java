package com.tassioauad.gamecatalog.view;

import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

public interface SearchPlatformView {
    void warnFailuredToListPlatforms();

    void showPlatform(List<Platform> platformList);

    void warnAnyPlatformFound();

    void showLoading();

    void dismissLoading();
}

package com.tassioauad.gamecatalog.view;


import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

public interface LastsPlatformView {
    void showLoading();

    void showPlatforms(List<Platform> platformList);

    void dismissLoading();

    void warnCouldNotLoadPlatforms();
}

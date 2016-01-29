package com.tassioauad.gamecheck.view;


import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;

public interface LastsPlatformView {
    void showLoading();

    void showPlatforms(List<Platform> platformList);

    void dismissLoading();

    void warnCouldNotLoadPlatforms();
}

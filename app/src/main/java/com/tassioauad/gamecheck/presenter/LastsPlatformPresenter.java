package com.tassioauad.gamecheck.presenter;


import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.view.LastsPlatformView;

import java.util.List;

public class LastsPlatformPresenter {

    private LastsPlatformView view;
    private PlatformApi platformApi;
    private final int NUMBER_OF_PLATFORMS = 20;

    public LastsPlatformPresenter(LastsPlatformView view, PlatformApi platformApi) {
        this.view = view;
        this.platformApi = platformApi;
    }

    public void loadLastsPlatforms() {
        view.showLoading();
        platformApi.setServiceResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                view.showPlatforms((List<Platform>) object);
                view.dismissLoading();
            }

            @Override
            public void onException(Exception exception) {
                view.dismissLoading();
                view.warnCouldNotLoadPlatforms();
            }
        });

        platformApi.searchLasts(NUMBER_OF_PLATFORMS);
    }
}

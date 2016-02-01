package com.tassioauad.gamecheck.presenter;

import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.view.SearchPlatformView;

import java.util.List;

public class SearchPlatformPresenter {

    private SearchPlatformView view;
    private PlatformApi platformApi;

    public SearchPlatformPresenter(SearchPlatformView view, PlatformApi platformApi) {
        this.view = view;
        this.platformApi = platformApi;
    }

    public void searchByName(String name) {
        view.showLoading();
        platformApi.setServiceResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Platform> platformList = (List<Platform>) object;
                if (platformList.size() > 0) {
                    view.showPlatforms(platformList);
                } else {
                    view.warnAnyPlatformFound();
                }
                view.dismissLoading();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailuredToListPlatforms();
                view.dismissLoading();
            }
        });

        platformApi.searchByName(name);
    }
}

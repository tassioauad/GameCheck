package com.tassioauad.gamecatalog.presenter;

import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.view.SearchPlatformView;

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
                    view.showPlatform(platformList);
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

package com.tassioauad.gamecatalog.presenter;


import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.view.LastsPlatformView;

public class LastsPlatformPresenter {

    private LastsPlatformView view;
    private PlatformApi platformApi;

    public LastsPlatformPresenter(LastsPlatformView view, PlatformApi platformApi) {
        this.view = view;
        this.platformApi = platformApi;
    }

    public void init() {

    }
}

package com.tassioauad.gamecatalog.presenter;


import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.view.LastsGameView;

public class LastsGamePresenter {

    private LastsGameView view;
    private GameApi gameApi;

    public LastsGamePresenter(LastsGameView view, GameApi gameApi) {
        this.view = view;
        this.gameApi = gameApi;
    }

    public void init() {

    }
}

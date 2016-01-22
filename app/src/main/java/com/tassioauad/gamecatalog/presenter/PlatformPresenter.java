package com.tassioauad.gamecatalog.presenter;

import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.view.PlatformView;

import java.util.List;

public class PlatformPresenter {

    private PlatformView view;
    private GameApi gameApi;
    private Platform platform;

    public PlatformPresenter(PlatformView view, GameApi gameApi) {
        this.view = view;
        this.gameApi = gameApi;
    }

    public void init(Platform platform) {
        this.platform = platform;
        view.showPlatform(platform);
        loadGames();
    }

    private void loadGames() {
        view.showLoadingMessage();
        gameApi.setServiceResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Game> gameList = (List<Game>) object;
                if(gameList != null && gameList.size() >0 ) {
                    view.showGames(gameList);
                } else {
                    view.warnNoGame();
                }
                view.hideLoadingMessage();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailureToListGames();
                view.hideLoadingMessage();
            }
        });
        gameApi.searchByPlatform(platform);
    }


}

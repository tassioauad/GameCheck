package com.tassioauad.gamecheck.presenter;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.database.PlatformRatingDao;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.view.PlatformView;

import java.util.List;

public class PlatformPresenter {

    private PlatformView view;
    private GameApi gameApi;
    private Platform platform;
    private PlatformRatingDao platformRatingDao;
    private Float rating;

    public PlatformPresenter(PlatformView view, GameApi gameApi, PlatformRatingDao platformRatingDao) {
        this.view = view;
        this.gameApi = gameApi;
        this.platformRatingDao = platformRatingDao;
    }

    public void init(Platform platform) {
        this.platform = platform;
        rating = platformRatingDao.getRatingOf(platform);
        view.showPlatform(platform);
        if(rating != null) {
            view.showRating(rating);
        }
    }

    public void loadGames() {
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

    public void setRating(float rating) {
        if(this.rating == null) {
            platformRatingDao.insert(platform, rating);
        } else {
            platformRatingDao.update(platform, rating);
        }
        this.rating = rating;
    }


}

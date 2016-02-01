package com.tassioauad.gamecheck.model.api;


import com.tassioauad.gamecheck.model.entity.Platform;

public interface GameApi extends AsyncService {

    void searchLasts(Integer count);

    void searchByName(String name);

    void searchByPlatform(Platform platform);

}

package com.tassioauad.gamecatalog.model.api;


import com.tassioauad.gamecatalog.model.entity.Platform;

public interface GameApi extends AsyncService {

    void searchLasts(Integer count);

    void searchByName(String name);

    void searchByPlatform(Platform platform);

}

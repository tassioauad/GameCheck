package com.tassioauad.gamecatalog.model.api;

public interface PlatformApi extends AsyncService {

    void searchLasts(Integer count);

    void searchByName(String name);

}

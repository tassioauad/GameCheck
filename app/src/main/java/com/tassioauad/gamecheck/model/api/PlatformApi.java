package com.tassioauad.gamecheck.model.api;

public interface PlatformApi extends AsyncService {

    void searchLasts(Integer count);

    void searchByName(String name);

}

package com.tassioauad.gamecatalog.model.api.asynctask;

public interface ApiResultListener {

    public void onResult(Object object);

    public void onException(Exception exception);

}

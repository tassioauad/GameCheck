package com.tassioauad.gamecheck.model.api.asynctask;

public interface ApiResultListener {

    public void onResult(Object object);

    public void onException(Exception exception);

}

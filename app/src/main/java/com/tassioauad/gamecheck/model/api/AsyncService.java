package com.tassioauad.gamecheck.model.api;

import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;

public interface AsyncService {

    ApiResultListener getServiceResultListener();

    void setServiceResultListener(ApiResultListener listener);

    void cancelAllService();
}

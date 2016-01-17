package com.tassioauad.gamecatalog.model.api;

import android.content.Context;

import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;


public abstract class GenericApi implements AsyncService {

    private Context context;

    private ApiResultListener listener;

    public GenericApi(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public ApiResultListener getServiceResultListener() {
        return listener;
    }

    public void setServiceResultListener(ApiResultListener listener) {
        this.listener = listener;
    }

    public void verifyServiceResultListener() {
        if (getServiceResultListener() == null) {
            throw new RuntimeException("Before invoke this method, you must set an ApiResultListener instance");
        }
    }

}

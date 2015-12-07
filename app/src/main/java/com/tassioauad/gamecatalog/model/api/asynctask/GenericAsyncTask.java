package com.tassioauad.gamecatalog.model.api.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.tassioauad.gamecatalog.R;

import retrofit.Retrofit;

public abstract class GenericAsyncTask<PARAM, POGRESS, RETURN> extends AsyncTask<PARAM, POGRESS, AsyncTaskResult<RETURN>> {

    private Context context;
    private ApiResultListener listener;

    public GenericAsyncTask(Context context, ApiResultListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return context.getString(R.string.giantbombapi_baseurl);
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<RETURN> returnAsyncTaskResult) {
        if (returnAsyncTaskResult.getResult() != null) {
            listener.onResult(returnAsyncTaskResult.getResult());
        } else if (returnAsyncTaskResult.getError() != null) {
            listener.onException(returnAsyncTaskResult.getError());
        } else {
            listener.onResult(returnAsyncTaskResult.getResult());
        }
    }


}
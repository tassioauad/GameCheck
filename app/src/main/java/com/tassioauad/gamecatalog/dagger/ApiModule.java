package com.tassioauad.gamecatalog.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.model.api.GameApi;
import com.tassioauad.gamecatalog.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.GameSearchByNameAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.GameSearchByPlatformAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.GameSearchLastsAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.PlatformSearchByNameAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.impl.PlatformSearchLastsAsyncTask;
import com.tassioauad.gamecatalog.model.api.impl.GameApiImpl;
import com.tassioauad.gamecatalog.model.api.impl.PlatformApiImpl;
import com.tassioauad.gamecatalog.model.api.resource.GameResource;
import com.tassioauad.gamecatalog.model.api.resource.PlatformResource;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(library = true, includes = AppModule.class)
public class ApiModule {

    @Provides
    public Retrofit provideRetrofit(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss")
                .create();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.giantbombapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public GameResource provideGameResource(Context context) {
        return provideRetrofit(context).create(GameResource.class);
    }

    @Provides
    public PlatformResource providePlatformResource(Context context) {
        return provideRetrofit(context).create(PlatformResource.class);
    }

    @Provides
    public GameApi provideGameApi(Context context) {
        GameSearchLastsAsyncTask gameSearchLastsAsyncTask =
                new GameSearchLastsAsyncTask(context, provideGameResource(context));
        GameSearchByNameAsyncTask gameSearchByNameAsyncTask =
                new GameSearchByNameAsyncTask(context, provideGameResource(context));
        GameSearchByPlatformAsyncTask gameSearchByPlatformAsyncTask =
                new GameSearchByPlatformAsyncTask(context, provideGameResource(context));

        return new GameApiImpl(context, gameSearchLastsAsyncTask, gameSearchByNameAsyncTask, gameSearchByPlatformAsyncTask);
    }

    @Provides
    public PlatformApi providePlatformApi(Context context) {
        PlatformSearchByNameAsyncTask platformSearchByNameAsyncTask =
                new PlatformSearchByNameAsyncTask(context, providePlatformResource(context));
        PlatformSearchLastsAsyncTask platformSearchLastsAsyncTask =
                new PlatformSearchLastsAsyncTask(context, providePlatformResource(context));

        return new PlatformApiImpl(context, platformSearchByNameAsyncTask, platformSearchLastsAsyncTask);

    }

}

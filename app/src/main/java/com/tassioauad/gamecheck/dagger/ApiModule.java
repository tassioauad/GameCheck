package com.tassioauad.gamecheck.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.impl.GameApiImpl;
import com.tassioauad.gamecheck.model.api.impl.PlatformApiImpl;
import com.tassioauad.gamecheck.model.api.resource.GameResource;
import com.tassioauad.gamecheck.model.api.resource.PlatformResource;

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
        return new GameApiImpl(context, provideGameResource(context));
    }

    @Provides
    public PlatformApi providePlatformApi(Context context) {
        return new PlatformApiImpl(context, providePlatformResource(context));

    }

}

package com.tassioauad.gamecatalog.dagger;

import com.tassioauad.gamecatalog.model.api.PlatformApi;
import com.tassioauad.gamecatalog.presenter.LastsPlatformPresenter;
import com.tassioauad.gamecatalog.view.LastsPlatformView;
import com.tassioauad.gamecatalog.view.fragment.LastsPlatformFragment;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class, injects = LastsPlatformFragment.class, library = true)
public class LastsPlatformViewModule {

    private LastsPlatformView view;

    public LastsPlatformViewModule(LastsPlatformView view) {
        this.view = view;
    }

    @Provides
    public LastsPlatformPresenter provideLastsPlatformPresenter(PlatformApi platformApi) {
        return new LastsPlatformPresenter(view, platformApi);
    }
}

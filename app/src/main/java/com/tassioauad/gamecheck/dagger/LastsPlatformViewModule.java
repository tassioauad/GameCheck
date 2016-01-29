package com.tassioauad.gamecheck.dagger;

import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.presenter.LastsPlatformPresenter;
import com.tassioauad.gamecheck.view.LastsPlatformView;
import com.tassioauad.gamecheck.view.fragment.LastsPlatformFragment;

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

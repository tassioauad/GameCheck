package com.tassioauad.gamecatalog.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.LastsPlatformViewModule;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.presenter.LastsPlatformPresenter;
import com.tassioauad.gamecatalog.view.LastsPlatformView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class LastsPlatformFragment extends Fragment implements LastsPlatformView {

    @Inject
    LastsPlatformPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsplatform, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsPlatformViewModule(this)).inject(this);

        presenter.init();

        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showPlatforms(List<Platform> platformList) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void warnCouldNotLoadPlatforms() {

    }
}

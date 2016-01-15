package com.tassioauad.gamecatalog.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.LastsGameViewModule;
import com.tassioauad.gamecatalog.presenter.LastsGamePresenter;
import com.tassioauad.gamecatalog.view.LastsGameView;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class LastsGameFragment extends Fragment implements LastsGameView {

    @Inject
    LastsGamePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsgame, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsGameViewModule(this)).inject(this);

        presenter.init();

        return view;
    }
}

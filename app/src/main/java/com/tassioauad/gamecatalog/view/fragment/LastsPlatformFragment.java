package com.tassioauad.gamecatalog.view.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.LastsPlatformViewModule;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.presenter.LastsPlatformPresenter;
import com.tassioauad.gamecatalog.view.LastsPlatformView;
import com.tassioauad.gamecatalog.view.adapter.OnItemClickListener;
import com.tassioauad.gamecatalog.view.adapter.PlatformListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LastsPlatformFragment extends Fragment implements LastsPlatformView {

    @Inject
    LastsPlatformPresenter presenter;

    @Bind(R.id.recyclerview_platforms)
    RecyclerView recyclerViewPlatforms;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    private List<Platform> platformList;
    private final String BUNDLE_KEY_PLATFORMLIST = "bundle_key_platformlist";
    private final int NUMBER_OF_COLUMNS = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsplatform, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsPlatformViewModule(this)).inject(this);

        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_PLATFORMLIST) != null) {
            platformList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_PLATFORMLIST);
            showPlatforms(platformList);
        } else {
            presenter.loadLastsPlatforms();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(platformList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_PLATFORMLIST, new ArrayList<Platform>(platformList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlatforms(List<Platform> platformList) {
        this.platformList = platformList;
        recyclerViewPlatforms.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewPlatforms.setAdapter(new PlatformListAdapter(platformList, new OnItemClickListener<Platform>() {
            @Override
            public void onClick(Platform platform) {
                Log.i("PLATFORM", platform.getName());
            }
        }));
        recyclerViewPlatforms.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnCouldNotLoadPlatforms() {

    }
}

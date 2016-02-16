package com.tassioauad.gamecheck.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tassioauad.gamecheck.GameCatalogApplication;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.dagger.LastsPlatformViewModule;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.presenter.LastsPlatformPresenter;
import com.tassioauad.gamecheck.view.LastsPlatformView;
import com.tassioauad.gamecheck.view.activity.PlatformActivity;
import com.tassioauad.gamecheck.view.activity.SearchPlatformActivity;
import com.tassioauad.gamecheck.view.adapter.OnItemClickListener;
import com.tassioauad.gamecheck.view.adapter.PlatformListAdapter;

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
    @Bind(R.id.textview_failuredtolistplatform)
    TextView textViewFailuredToListPlatform;
    @Bind(R.id.floatingactionbutton_search)
    FloatingActionButton floatingActionButtonSearch;

    private List<Platform> platformList;
    private final String BUNDLE_KEY_PLATFORMLIST = "bundle_key_platformlist";
    private final int NUMBER_OF_COLUMNS = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsplatform, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsPlatformViewModule(this)).inject(this);

        textViewFailuredToListPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadLastsPlatforms();
            }
        });

        floatingActionButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchPlatformActivity.newInstance(getActivity()));
            }
        });

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
        recyclerViewPlatforms.setVisibility(View.GONE);
        textViewFailuredToListPlatform.setVisibility(View.GONE);
    }

    @Override
    public void showPlatforms(List<Platform> platformList) {
        this.platformList = platformList;
        textViewFailuredToListPlatform.setVisibility(View.GONE);
        recyclerViewPlatforms.setVisibility(View.VISIBLE);
        recyclerViewPlatforms.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewPlatforms.setAdapter(new PlatformListAdapter(platformList, new OnItemClickListener<Platform>() {
            @Override
            public void onClick(Platform platform) {
                startActivity(PlatformActivity.newInstance(getActivity(), platform));
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
        recyclerViewPlatforms.setVisibility(View.GONE);
        textViewFailuredToListPlatform.setVisibility(View.VISIBLE);
    }
}

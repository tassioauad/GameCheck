package com.tassioauad.gamecatalog.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.LastsGameViewModule;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.presenter.LastsGamePresenter;
import com.tassioauad.gamecatalog.view.LastsGameView;
import com.tassioauad.gamecatalog.view.adapter.GameListAdapter;
import com.tassioauad.gamecatalog.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LastsGameFragment extends Fragment implements LastsGameView {

    @Inject
    LastsGamePresenter presenter;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerview_games)
    RecyclerView recyclerViewGames;

    private final int NUMBER_OF_COLUMNS = 2;
    private final String BUNDLE_KEY_GAMELIST = "bundle_key_gamelist";
    private List<Game> gamesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsgame, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsGameViewModule(this)).inject(this);

        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST) != null) {
            gamesList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST);
            showGames(gamesList);
        } else {
            presenter.loadLastsGames();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(gamesList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_GAMELIST, new ArrayList<Game>(gamesList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnCouldNotLoadGames() {

    }

    @Override
    public void showGames(List<Game> gameList) {
        this.gamesList = gameList;
        recyclerViewGames.setAdapter(new GameListAdapter(gameList, new OnItemClickListener<Game>() {
            @Override
            public void onClick(Game game) {
                Log.i("GAME", game.getName());
            }
        }));
        recyclerViewGames.setLayoutManager(new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewGames.setItemAnimator(new DefaultItemAnimator());

    }

}

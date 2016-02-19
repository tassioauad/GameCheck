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
import com.tassioauad.gamecheck.dagger.LastsGameViewModule;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.presenter.LastsGamePresenter;
import com.tassioauad.gamecheck.view.LastsGameView;
import com.tassioauad.gamecheck.view.activity.GameActivity;
import com.tassioauad.gamecheck.view.activity.SearchGameActivity;
import com.tassioauad.gamecheck.view.adapter.GameListAdapter;
import com.tassioauad.gamecheck.view.adapter.OnItemClickListener;

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
    @Bind(R.id.textview_failuredtolistgame)
    TextView textViewFailuredToListGame;
    @Bind(R.id.floatingactionbutton_search)
    FloatingActionButton floatingActionButtonSearch;

    private final int NUMBER_OF_COLUMNS = 2;
    private final String BUNDLE_KEY_GAMELIST = "bundle_key_gamelist";
    private List<Game> gamesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastsgame, container, false);
        ButterKnife.bind(this, view);
        ((GameCatalogApplication) getActivity().getApplication()).getObjectGraph().plus(new LastsGameViewModule(this)).inject(this);

        textViewFailuredToListGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadLastsGames();
            }
        });

        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST) != null) {
            gamesList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST);
            showGames(gamesList);
        } else {
            presenter.loadLastsGames();
        }

        floatingActionButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchGameActivity.newInstance(getActivity()));
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(gamesList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_GAMELIST, new ArrayList<>(gamesList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewGames.setVisibility(View.GONE);
        textViewFailuredToListGame.setVisibility(View.GONE);

    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnCouldNotLoadGames() {
        textViewFailuredToListGame.setVisibility(View.VISIBLE);
        recyclerViewGames.setVisibility(View.GONE);
    }

    @Override
    public void showGames(List<Game> gameList) {
        this.gamesList = gameList;
        textViewFailuredToListGame.setVisibility(View.GONE);
        recyclerViewGames.setVisibility(View.VISIBLE);
        recyclerViewGames.setAdapter(new GameListAdapter(gameList, new OnItemClickListener<Game>() {
            @Override
            public void onClick(Game game) {
                startActivity(GameActivity.newInstance(getActivity(), game));
            }
        }));
        recyclerViewGames.setLayoutManager(new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewGames.setItemAnimator(new DefaultItemAnimator());

    }

}

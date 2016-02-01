package com.tassioauad.gamecheck.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tassioauad.gamecheck.GameCatalogApplication;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.dagger.SearchGameViewModule;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.presenter.SearchGamePresenter;
import com.tassioauad.gamecheck.view.SearchGameView;
import com.tassioauad.gamecheck.view.adapter.GameListAdapter;
import com.tassioauad.gamecheck.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchGameActivity extends AppCompatActivity implements SearchGameView {

    @Inject
    SearchGamePresenter presenter;
    private final int numberOfColumns = 2;
    private List<Game> gameList;
    private String name;
    private static final String BUNDLE_KEY_GAMELIST = "bundle_key_gamelist";
    private static final String BUNDLE_KEY_NAME = "bundle_key_name";

    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerview_games)
    RecyclerView recyclerViewGames;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.textview_nogame)
    TextView textViewNoGame;
    @Bind(R.id.textview_connectionfailed)
    TextView textViewConnectionFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchgame);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new SearchGameViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewConnectionFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchByName(name);
            }
        });

        if(savedInstanceState != null) {
            if(savedInstanceState.getString(BUNDLE_KEY_NAME) != null) {
                name = savedInstanceState.getString(BUNDLE_KEY_NAME);
                getSupportActionBar().setSubtitle(name);
            }
            if(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST) != null) {
                gameList  = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST);
                showGames(gameList);
            } else if(name != null){
                presenter.searchByName(name);
            }
        }
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, SearchGameActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchgame, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        menu.findItem(R.id.search).expandActionView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void startActivity(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            name = intent.getStringExtra(SearchManager.QUERY);
            presenter.searchByName(name);
            getSupportActionBar().setSubtitle(name);
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(gameList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_GAMELIST, new ArrayList<Parcelable>(gameList));
        }
        if(name != null) {
            outState.putString(BUNDLE_KEY_NAME, name);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void warnFailuredToListGame() {
        textViewNoGame.setVisibility(View.GONE);
        recyclerViewGames.setVisibility(View.GONE);
        textViewConnectionFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGames(List<Game> gameList) {
        this.gameList = gameList;
        textViewNoGame.setVisibility(View.GONE);
        recyclerViewGames.setVisibility(View.VISIBLE);
        textViewConnectionFailed.setVisibility(View.GONE);
        recyclerViewGames.setAdapter(new GameListAdapter(gameList, new OnItemClickListener<Game>() {
            @Override
            public void onClick(Game game) {
                startActivity(GameActivity.newInstance(SearchGameActivity.this, game));
            }
        }));
        recyclerViewGames.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewGames.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void warnAnyGameFound() {
        textViewNoGame.setVisibility(View.VISIBLE);
        recyclerViewGames.setVisibility(View.GONE);
        textViewConnectionFailed.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
    textViewNoGame.setVisibility(View.GONE);
    recyclerViewGames.setVisibility(View.GONE);
    textViewConnectionFailed.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }
}

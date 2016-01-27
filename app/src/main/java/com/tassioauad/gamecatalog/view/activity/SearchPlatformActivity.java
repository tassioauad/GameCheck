package com.tassioauad.gamecatalog.view.activity;

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

import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.SearchGameViewModule;
import com.tassioauad.gamecatalog.dagger.SearchPlatformViewModule;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.presenter.SearchGamePresenter;
import com.tassioauad.gamecatalog.presenter.SearchPlatformPresenter;
import com.tassioauad.gamecatalog.view.SearchPlatformView;
import com.tassioauad.gamecatalog.view.adapter.OnItemClickListener;
import com.tassioauad.gamecatalog.view.adapter.PlatformListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchPlatformActivity extends AppCompatActivity implements SearchPlatformView {

    @Inject
    SearchPlatformPresenter presenter;
    private final int numberOfColumns = 2;
    private List<Platform> platformList;
    private String name;
    private static final String BUNDLE_KEY_PLATFORMLIST = "bundle_key_platformlist";
    private static final String BUNDLE_KEY_NAME = "bundle_key_name";

    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerview_platforms)
    RecyclerView recyclerViewPlatforms;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.textview_noplatform)
    TextView textViewNoPlatform;
    @Bind(R.id.textview_connectionfailed)
    TextView textViewConnectionFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchplatform);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new SearchPlatformViewModule(this)).inject(this);

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
            if(savedInstanceState.getParcelableArrayList(BUNDLE_KEY_PLATFORMLIST) != null) {
                platformList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_PLATFORMLIST);
                showPlatform(platformList);
            } else if(name != null){
                presenter.searchByName(name);
            }
        }
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, SearchPlatformActivity.class);
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
        if(platformList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_PLATFORMLIST, new ArrayList<Parcelable>(platformList));
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
    public void warnFailuredToListPlatforms() {
        textViewNoPlatform.setVisibility(View.GONE);
        recyclerViewPlatforms.setVisibility(View.GONE);
        textViewConnectionFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlatform(List<Platform> platformList) {
        this.platformList = platformList;
        textViewNoPlatform.setVisibility(View.GONE);
        recyclerViewPlatforms.setVisibility(View.VISIBLE);
        textViewConnectionFailed.setVisibility(View.GONE);
        recyclerViewPlatforms.setAdapter(new PlatformListAdapter(platformList, new OnItemClickListener<Platform>() {
            @Override
            public void onClick(Platform platform) {
                startActivity(PlatformActivity.newInstance(SearchPlatformActivity.this, platform));
            }
        }));
        recyclerViewPlatforms.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewPlatforms.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void warnAnyPlatformFound() {
        textViewNoPlatform.setVisibility(View.VISIBLE);
        recyclerViewPlatforms.setVisibility(View.GONE);
        textViewConnectionFailed.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
    textViewNoPlatform.setVisibility(View.GONE);
    recyclerViewPlatforms.setVisibility(View.GONE);
    textViewConnectionFailed.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }
}

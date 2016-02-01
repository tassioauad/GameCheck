package com.tassioauad.gamecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecheck.GameCatalogApplication;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.dagger.PlatformViewModule;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.presenter.PlatformPresenter;
import com.tassioauad.gamecheck.view.FullImageDialogFragment;
import com.tassioauad.gamecheck.view.PlatformView;
import com.tassioauad.gamecheck.view.adapter.GameListAdapter;
import com.tassioauad.gamecheck.view.adapter.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlatformActivity extends AppCompatActivity implements PlatformView{

    @Inject
    PlatformPresenter presenter;
    private static final String INTENT_KEY_PLATFORM = "intent_key_platform";
    private static final String BUNDLE_KEY_GAMELIST = "bundle_key_gamelist";
    private List<Game> gameList;

    @Bind(R.id.recyclerview_platforms)
    RecyclerView recyclerView;
    @Bind(R.id.textview_name)
    TextView textViewName;
    @Bind(R.id.textview_realeasedate)
    TextView textViewResleaseDate;
    @Bind(R.id.imageview_cover)
    ImageView imageViewCover;
    @Bind(R.id.imageview_photo)
    ImageView imageViewPhoto;
    @Bind(R.id.textview_description)
    TextView textViewDescription;
    @Bind(R.id.textview_nogame)
    TextView textViewNoGame;
    @Bind(R.id.textview_failuredTolist)
    TextView textViewFailuredToList;
    @Bind(R.id.linearlayout_loading)
    LinearLayout linearLayoutLoading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new PlatformViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Platform platform = getIntent().getParcelableExtra(INTENT_KEY_PLATFORM);
        presenter.init(platform);
        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST) != null) {
            gameList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_GAMELIST);
            showGames(gameList);
        } else {
            presenter.loadGames();
        }

        textViewFailuredToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadGames();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                presenter.setRating(rating);
            }
        });

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullImageDialogFragment.newInstance(platform.getImage() != null ? platform.getImage().getMediumUrl() : null)
                        .show(getSupportFragmentManager(), "fullimagedialog");
            }
        });
        imageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullImageDialogFragment.newInstance(platform.getImage() != null ? platform.getImage().getSuperUrl() : null)
                        .show(getSupportFragmentManager(), "fullimagedialog");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(gameList != null) {
            outState.putParcelableArrayList(BUNDLE_KEY_GAMELIST, new ArrayList<Parcelable>(gameList));
        }

        super.onSaveInstanceState(outState);
    }

    public static final Intent newInstance(Context context, Platform platform) {
        Intent intent = new Intent(context, PlatformActivity.class);
        intent.putExtra(INTENT_KEY_PLATFORM, platform);

        return intent;
    }

    @Override
    public void showGames(List<Game> gameList) {
        this.gameList = gameList;
        textViewNoGame.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.GONE);
        textViewFailuredToList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new GameListAdapter(gameList, new OnItemClickListener<Game>() {
            @Override
            public void onClick(Game game) {
                startActivity(GameActivity.newInstance(PlatformActivity.this, game));
            }
        }));
        int numberOfColumns = 3;
        if(getResources().getConfiguration().orientation  == Configuration.ORIENTATION_LANDSCAPE) {
            numberOfColumns = 2;
        }
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showPlatform(Platform platform) {
        if(platform.getReleaseDate() != null) {
            String dateRealease = new SimpleDateFormat(getString(R.string.date_format)).format(platform.getReleaseDate());
            textViewResleaseDate.setText(String.format(getString(R.string.platformactivity_realeasedate), dateRealease));
        } else {
            textViewResleaseDate.setText(String.format(getString(R.string.platformactivity_realeasedate), "--/--/----"));
        }
        textViewName.setText(platform.getName());
        if(platform.getImage() != null) {
            Picasso.with(this).load(platform.getImage().getMediumUrl()).placeholder(R.drawable.nophoto).into(imageViewPhoto);
            Picasso.with(this).load(platform.getImage().getSuperUrl()).placeholder(R.drawable.nophoto).into(imageViewCover);
        }
        if(platform.getDescription() != null && !platform.getDescription().equals("<p style=\"\"> </p>")) {
            textViewDescription.setText(Html.fromHtml(platform.getDeck().replace("<h2>", "<h4>").replace("</h2>", "</h4>")));
        }
    }

    @Override
    public void warnNoGame() {
        recyclerView.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.GONE);
        textViewNoGame.setVisibility(View.VISIBLE);
        textViewFailuredToList.setVisibility(View.GONE);
    }

    @Override
    public void warnFailureToListGames() {
        textViewFailuredToList.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        textViewNoGame.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.GONE);
        textViewNoGame.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.VISIBLE);
        textViewFailuredToList.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        linearLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showRating(Float rating) {
        ratingBar.setRating(rating);
    }

}

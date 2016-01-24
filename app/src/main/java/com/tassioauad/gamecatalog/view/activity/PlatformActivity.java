package com.tassioauad.gamecatalog.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.GameViewModule;
import com.tassioauad.gamecatalog.dagger.PlatformViewModule;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.presenter.GamePresenter;
import com.tassioauad.gamecatalog.presenter.PlatformPresenter;
import com.tassioauad.gamecatalog.view.PlatformView;
import com.tassioauad.gamecatalog.view.adapter.GameListAdapter;
import com.tassioauad.gamecatalog.view.adapter.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlatformActivity extends AppCompatActivity implements PlatformView{

    @Inject
    PlatformPresenter presenter;
    private static final String INTENT_KEY_PLATFORM = "intent_key_platform";

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
    @Bind(R.id.linearlayout_loading)
    LinearLayout linearLayoutLoading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.scrollview_description)
    ScrollView scrollViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new PlatformViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Platform platform = getIntent().getParcelableExtra(INTENT_KEY_PLATFORM);
        presenter.init(platform);
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

    public static final Intent newInstance(Context context, Platform platform) {
        Intent intent = new Intent(context, PlatformActivity.class);
        intent.putExtra(INTENT_KEY_PLATFORM, platform);

        return intent;
    }

    @Override
    public void showGames(List<Game> gameList) {
        textViewNoGame.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new GameListAdapter(gameList, new OnItemClickListener<Game>() {
            @Override
            public void onClick(Game game) {
                startActivity(GameActivity.newInstance(PlatformActivity.this, game));
            }
        }));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
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
            textViewDescription.setText(Html.fromHtml(platform.getDescription().replace("<h2>", "<h4>").replace("</h2>", "</h4>")));
        } else {
            scrollViewDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public void warnNoGame() {
        recyclerView.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.GONE);
        textViewNoGame.setVisibility(View.VISIBLE);
    }

    @Override
    public void warnFailureToListGames() {
        warnNoGame();
        Toast toast = Toast.makeText(this, R.string.platformactivity_failuretolistgames, Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(getColor(R.color.red));
        toast.show();
    }

    @Override
    public void showLoadingMessage() {
        recyclerView.setVisibility(View.GONE);
        textViewNoGame.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMessage() {
        linearLayoutLoading.setVisibility(View.GONE);
    }

}

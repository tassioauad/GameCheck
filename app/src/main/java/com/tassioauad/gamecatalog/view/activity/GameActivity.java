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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecatalog.GameCatalogApplication;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.dagger.GameViewModule;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;
import com.tassioauad.gamecatalog.presenter.GamePresenter;
import com.tassioauad.gamecatalog.view.GameView;
import com.tassioauad.gamecatalog.view.adapter.OnItemClickListener;
import com.tassioauad.gamecatalog.view.adapter.PlatformListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity implements GameView {

    @Inject
    GamePresenter presenter;
    private static final String INTENT_KEY_GAME = "intent_key_game";

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
    @Bind(R.id.textview_noplatform)
    TextView textViewNoPlatform;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new GameViewModule(this)).inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Game game = getIntent().getParcelableExtra(INTENT_KEY_GAME);
        presenter.init(game);
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

    public static final Intent newInstance(Context context, Game game) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(INTENT_KEY_GAME, game);

        return intent;
    }

    @Override
    public void showPlataforms(List<Platform> platformList) {
        recyclerView.setAdapter(new PlatformListAdapter(platformList, new OnItemClickListener<Platform>() {
            @Override
            public void onClick(Platform platform) {
                startActivity(PlatformActivity.newInstance(GameActivity.this, platform));
            }
        }));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showGame(Game game) {
        Date realeaseDate = game.getOriginalReleaseDate() == null ? game.getExpectedReleaseDate() : game.getOriginalReleaseDate();
        if(realeaseDate != null) {
            String dateRealease = new SimpleDateFormat(getString(R.string.date_format)).format(realeaseDate);
            textViewResleaseDate.setText(String.format(getString(R.string.gameactivity_realeasedate), dateRealease));
        } else {
            textViewResleaseDate.setText(String.format(getString(R.string.gameactivity_realeasedate), "--/--/----"));
        }
        textViewName.setText(game.getName());
        if(game.getImage() != null) {
            Picasso.with(this).load(game.getImage().getMediumUrl()).placeholder(R.drawable.nophoto).into(imageViewPhoto);
            Picasso.with(this).load(game.getImage().getSuperUrl()).placeholder(R.drawable.nophoto).into(imageViewCover);
        }
        if(game.getDescription() != null) {
            textViewDescription.setText(Html.fromHtml(game.getDescription().replace("<h2>", "<h4>").replace("</h2>", "</h4>")));
        } else {
            textViewDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public void warnNoPlatform() {
        recyclerView.setVisibility(View.GONE);
        textViewNoPlatform.setVisibility(View.VISIBLE);
    }
}

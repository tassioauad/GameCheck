package com.tassioauad.gamecheck.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tassioauad.gamecheck.GameCatalogApplication;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.dagger.HomeViewModule;
import com.tassioauad.gamecheck.presenter.HomePresenter;
import com.tassioauad.gamecheck.view.HomeView;
import com.tassioauad.gamecheck.view.fragment.LastsGameFragment;
import com.tassioauad.gamecheck.view.fragment.LastsPlatformFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @Inject
    HomePresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((GameCatalogApplication) getApplication()).getObjectGraph().plus(new HomeViewModule(this)).inject(this);

        viewPager.setAdapter(new PagerAdapter());

        setSupportActionBar(toolbar);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                case 0:
                    return new LastsGameFragment();
                case 1:
                    return new LastsPlatformFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                default:
                case 0:
                    return getString(R.string.lastsgamefragment_title);
                case 1:
                    return getString(R.string.lastsplatformfragment_title);
            }
        }
    }
}

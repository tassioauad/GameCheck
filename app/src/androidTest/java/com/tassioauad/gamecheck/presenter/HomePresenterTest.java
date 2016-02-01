package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.view.HomeView;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;


public class HomePresenterTest extends AndroidTestCase {

    HomePresenter presenter;
    HomeView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        view = mock(HomeView.class);
        presenter = new HomePresenter(view);

    }
}
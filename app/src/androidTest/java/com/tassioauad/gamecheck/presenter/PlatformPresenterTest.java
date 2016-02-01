package com.tassioauad.platformcheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.database.PlatformRatingDao;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.presenter.PlatformPresenter;
import com.tassioauad.gamecheck.view.PlatformView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlatformPresenterTest extends AndroidTestCase {

    PlatformPresenter presenter;
    PlatformView view;
    PlatformRatingDao platformRatingDao;
    GameApi gameApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(PlatformView.class);
        platformRatingDao = mock(PlatformRatingDao.class);
        gameApi = mock(GameApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new PlatformPresenter(view, gameApi, platformRatingDao);
    }

    public void testInit_WithRating() {
        Float rating = 2f;
        Platform platform = PlatformBuilder.aPlatform().build();
        when(platformRatingDao.getRatingOf(any(Platform.class))).thenReturn(rating);

        presenter.init(platform);

        verify(view, times(1)).showPlatform(platform);
        verify(view, times(1)).showRating(rating);
        verify(platformRatingDao, times(1)).getRatingOf(platform);
    }

    public void testInit_WithoutRating() {
        Platform platform = PlatformBuilder.aPlatform().build();
        when(platformRatingDao.getRatingOf(any(Platform.class))).thenReturn(null);

        presenter.init(platform);

        verify(view, times(1)).showPlatform(platform);
        verify(view, never()).showRating(anyFloat());
        verify(platformRatingDao, times(1)).getRatingOf(platform);
    }

    public void testLoadGames_EmptyListLoaded() {
        final ArrayList<Game> gameArrayList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(gameArrayList);
                        return null;
                    }
                }).when(gameApi).searchLasts(anyInt());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        verify(view, never()).showGames(gameArrayList);
        verify(view, times(1)).warnNoGame();
        verify(view, never()).warnFailureToListGames();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testLoadGames_ListLoaded() {
        final ArrayList<Game> gameArrayList = new ArrayList<>();
        gameArrayList.add(GameBuilder.aGame().build());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(gameArrayList);
                        return null;
                    }
                }).when(gameApi).searchLasts(anyInt());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        verify(view, times(1)).showGames(gameArrayList);
        verify(view, never()).warnNoGame();
        verify(view, never()).warnFailureToListGames();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testLoadGames_Exception() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(gameApi).searchLasts(anyInt());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        verify(view, never()).showGames(anyListOf(Game.class));
        verify(view, never()).warnNoGame();
        verify(view, times(1)).warnFailureToListGames();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testSetRating_RatingNull() {
        float rating = 1f;

        presenter.setRating(rating);

        verify(platformRatingDao, times(1)).insert(any(Platform.class), rating);
        verify(platformRatingDao, never()).update(any(Platform.class), rating);
    }

    public void testSetRating_RatingNotNull() {
        float firstRating = 1f;
        float secondRating = 2f;
        when(platformRatingDao.getRatingOf(any(Platform.class))).thenReturn(firstRating);

        presenter.init(PlatformBuilder.aPlatform().build());
        presenter.setRating(secondRating);

        verify(platformRatingDao, never()).insert(any(Platform.class), secondRating);
        verify(platformRatingDao, times(1)).update(any(Platform.class), secondRating);
    }
    
}
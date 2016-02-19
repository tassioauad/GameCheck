package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.SearchGameView;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SearchGamePresenterTest extends AndroidTestCase {

    SearchGamePresenter presenter;
    SearchGameView view;
    GameApi gameApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(SearchGameView.class);
        gameApi = mock(GameApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new SearchGamePresenter(view, gameApi);
    }

    public void testSearchByGame_EmptyList() {
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
                }).when(gameApi).searchByName(anyString());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(GameBuilder.DEFAULT_NAME);

        verify(view, never()).showGames(gameArrayList);
        verify(view, times(1)).warnAnyGameFound();
        verify(view, never()).warnFailuredToListGame();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testSearchByGame_List() {
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
                }).when(gameApi).searchByName(anyString());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(GameBuilder.DEFAULT_NAME);

        verify(view, times(1)).showGames(gameArrayList);
        verify(view, never()).warnAnyGameFound();
        verify(view, never()).warnFailuredToListGame();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testSearchByGame_Exception() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(gameApi).searchByName(anyString());
                return null;
            }
        }).when(gameApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(GameBuilder.DEFAULT_NAME);

        verify(view, never()).showGames(anyListOf(Game.class));
        verify(view, never()).warnAnyGameFound();
        verify(view, times(1)).warnFailuredToListGame();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }
}
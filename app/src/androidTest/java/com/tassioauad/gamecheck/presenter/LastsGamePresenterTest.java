package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.LastsGameView;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class LastsGamePresenterTest extends AndroidTestCase {

    LastsGamePresenter presenter;
    LastsGameView view;
    GameApi gameApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(LastsGameView.class);
        gameApi = mock(GameApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new LastsGamePresenter(view, gameApi);
    }

    public void testLoadLastsGames_SuccessfulLoad() {
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

        presenter.loadLastsGames();

        verify(view, times(1)).showGames(gameArrayList);
        verify(view, never()).warnCouldNotLoadGames();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testLoadLastsGames_FailureToLoad() {
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

        presenter.loadLastsGames();

        verify(view, never()).showGames(anyListOf(Game.class));
        verify(view, times(1)).warnCouldNotLoadGames();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

}
package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.view.LastsPlatformView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LastsPlatformPresenterTest extends AndroidTestCase {

    LastsPlatformPresenter presenter;
    LastsPlatformView view;
    PlatformApi platformApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(LastsPlatformView.class);
        platformApi = mock(PlatformApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new LastsPlatformPresenter(view, platformApi);
    }

    public void testLoadLastsPlatforms_SuccessfulLoad() {
        final ArrayList<Platform> platformArrayList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(platformArrayList);
                        return null;
                    }
                }).when(platformApi).searchLasts(anyInt());
                return null;
            }
        }).when(platformApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.loadLastsPlatforms();

        verify(view, times(1)).showPlatforms(platformArrayList);
        verify(view, never()).warnCouldNotLoadPlatforms();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testLoadLastsPlatforms_FailureToLoad() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(platformApi).searchLasts(anyInt());
                return null;
            }
        }).when(platformApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.loadLastsPlatforms();

        verify(view, never()).showPlatforms(anyListOf(Platform.class));
        verify(view, times(1)).warnCouldNotLoadPlatforms();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }


}
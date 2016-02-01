package com.tassioauad.gamecheck.presenter;

import android.test.AndroidTestCase;

import com.tassioauad.gamecheck.entity.GameBuilder;
import com.tassioauad.gamecheck.entity.PlatformBuilder;
import com.tassioauad.gamecheck.model.api.PlatformApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Platform;
import com.tassioauad.gamecheck.view.SearchPlatformView;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SearchPlatformPresenterTest extends AndroidTestCase {

    SearchPlatformPresenter presenter;
    SearchPlatformView view;
    PlatformApi platformApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(SearchPlatformView.class);
        platformApi = mock(PlatformApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
        presenter = new SearchPlatformPresenter(view, platformApi);
    }

    public void testSearchByPlatform_EmptyList() {
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
                }).when(platformApi).searchByName(anyString());
                return null;
            }
        }).when(platformApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(PlatformBuilder.DEFAULT_NAME);

        verify(view, never()).showPlatforms(platformArrayList);
        verify(view, times(1)).warnAnyPlatformFound();
        verify(view, never()).warnFailuredToListPlatforms();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testSearchByPlatform_List() {
        final ArrayList<Platform> platformArrayList = new ArrayList<>();
        platformArrayList.add(PlatformBuilder.aPlatform().build());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onResult(platformArrayList);
                        return null;
                    }
                }).when(platformApi).searchByName(anyString());
                return null;
            }
        }).when(platformApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(PlatformBuilder.DEFAULT_NAME);

        verify(view, times(1)).showPlatforms(platformArrayList);
        verify(view, never()).warnAnyPlatformFound();
        verify(view, never()).warnFailuredToListPlatforms();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

    public void testSearchByPlatform_Exception() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(platformApi).searchByName(anyString());
                return null;
            }
        }).when(platformApi).setServiceResultListener(apiResultListenerArgumentCaptor.capture());

        presenter.searchByName(PlatformBuilder.DEFAULT_NAME);

        verify(view, never()).showPlatforms(anyListOf(Platform.class));
        verify(view, never()).warnAnyPlatformFound();
        verify(view, times(1)).warnFailuredToListPlatforms();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).dismissLoading();
    }

}
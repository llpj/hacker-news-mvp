package com.emmaguy.hn.presenter;

import com.emmaguy.hn.model.data.HackerNewsDataSource;
import com.emmaguy.hn.presenter.comments.CommentsPresenterImpl;
import com.emmaguy.hn.view.CommentsView;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by emma on 21/03/15.
 */
public class CommentsPresenterImplTest {
    @Mock
    private Bus mMockNetworkBus;

    @Mock
    private CommentsView mMockView;

    @Mock
    private HackerNewsDataSource mMockDataSource;

    private CommentsPresenterImpl mPresenter;

    private ArrayList<String> mIds;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new CommentsPresenterImpl(mMockView, mIds, mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void test_onStart_registersNetworkBus() {
        mPresenter.onStart();

        verify(mMockNetworkBus, times(1)).register(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void test_onStop_unregistersNetworkBus() {
        mPresenter.onStop();

        verify(mMockNetworkBus, times(1)).unregister(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }
}

package com.emmaguy.hn.model.data;

import com.emmaguy.hn.model.data.newsitems.NewsItemsRequestFailedEvent;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemErrorListener;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by emma on 21/03/15.
 */
public class OnListNewsItemErrorListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnListNewsItemErrorListener mErrorListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mErrorListener = new OnListNewsItemErrorListener(mMockNetworkBus);
    }

    @Test
    public void test_errorListener_postsRequestFailedOnEventBus() {
        mErrorListener.call(new Throwable("blah"));

        verify(mMockNetworkBus, times(1)).post(any(NewsItemsRequestFailedEvent.class));
        verifyNoMoreInteractions(mMockNetworkBus);
    }
}

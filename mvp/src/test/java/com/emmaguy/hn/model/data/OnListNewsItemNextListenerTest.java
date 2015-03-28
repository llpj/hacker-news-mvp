package com.emmaguy.hn.model.data;

import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemNextListener;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnListNewsItemNextListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnListNewsItemNextListener mNextListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNextListener = new OnListNewsItemNextListener(mMockNetworkBus);
    }

    @Test
    public void test_getLatestNewsItems_postsNewsItemsOnEventBus() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem());

        mNextListener.call(newsItems);

        verify(mMockNetworkBus, times(1)).post(newsItems);
    }

    @Test
    public void test_postingNewsItems_sortsNewsItems() {
        NewsItem item1 = new NewsItem();
        item1.setScore(10);

        NewsItem item2 = new NewsItem();
        item2.setScore(50);

        NewsItem item3 = new NewsItem();
        item3.setScore(30);

        List<NewsItem> unsortedNewsItems = Arrays.asList(item1, item2, item3);
        mNextListener.call(unsortedNewsItems);

        verify(mMockNetworkBus, times(1)).post(unsortedNewsItems);

        assertThat(unsortedNewsItems, contains(item2, item3, item1));
    }
}
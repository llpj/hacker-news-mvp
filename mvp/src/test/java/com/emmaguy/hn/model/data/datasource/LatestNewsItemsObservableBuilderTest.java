package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.HackerNewsApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by emma on 29/03/15.
 */
public class LatestNewsItemsObservableBuilderTest {

    @Mock
    private HackerNewsApiService mApiService;

    private Observable<List<NewsItem>> mObservable;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        when(mApiService.topStories()).thenReturn(Observable.just(Arrays.asList("1", "2", "3", "4", "5", "6", "7")));

        mObservable = new LatestNewsItemsObservableBuilder(5, mApiService).build();
//        mObservable = Observable.just(Arrays.asList(new NewsItem("1")));
    }

    @Test
    public void test_builtObservable_limitsTo5NewsItemsAndPreservesInputOrder() {
        final NewsItem item1 = new NewsItem("1");
        final NewsItem item2 = new NewsItem("2");
        final NewsItem item3 = new NewsItem("3");
        final NewsItem item4 = new NewsItem("4");
        final NewsItem item5 = new NewsItem("5");

        when(mApiService.item("1")).thenReturn(Observable.just(item1));
        when(mApiService.item("2")).thenReturn(Observable.just(item2));
        when(mApiService.item("3")).thenReturn(Observable.just(item3));
        when(mApiService.item("4")).thenReturn(Observable.just(item4));
        when(mApiService.item("5")).thenReturn(Observable.just(item5));

        mObservable.subscribe(new Action1<List<NewsItem>>() {
            @Override
            public void call(List<NewsItem> newsItems) {
                assertThat(newsItems.size(), equalTo(5));
                assertThat(newsItems, contains(item1, item2, item3, item4, item5));
                assertEquals(false, true);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                assertEquals(false, true);
            }
        });
    }
}

package com.emmaguy.hn.model.data;

import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.NewsItemComparator;
import com.squareup.otto.Bus;

import java.util.Collections;
import java.util.List;

/**
 * Created by emma on 21/03/15.
 */
public class OnListNewsItemNextListener implements rx.functions.Action1<java.util.List<NewsItem>> {
    private Bus mNetworkBus;

    public OnListNewsItemNextListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(List<NewsItem> newsItems) {
        // using rxjava's 'toSortedList' causes it to create an unmodifiable list wrapper which we do not want
        // as then otto won't receive the items. So we have to sort the items manually
        Collections.sort(newsItems, new NewsItemComparator());

        mNetworkBus.post(newsItems);
    }
}


package com.emmaguy.hn.presenter.newsitems;

import android.support.annotation.NonNull;

import com.emmaguy.hn.model.data.Events;
import com.emmaguy.hn.model.data.HackerNewsDataSource;
import com.emmaguy.hn.view.NewsItemsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenterImpl implements NewsItemsPresenter {
    private final Bus mNetworkBus;
    private final NewsItemsView mNewsItemsView;
    private final HackerNewsDataSource mDataSource;

    public NewsItemsPresenterImpl(NewsItemsView newsItemsView, HackerNewsDataSource dataSource, Bus networkBus) {
        mNetworkBus = networkBus;
        mDataSource = dataSource;
        mNewsItemsView = newsItemsView;
    }

    @Override
    public void onStart() {
        mNetworkBus.register(this);

        if (mNewsItemsView.isEmptyList()) {
            mNewsItemsView.hideError();
            mNewsItemsView.showLoadingIndicator();

            mDataSource.getLatestNewsItems();
        }
    }

    @Override
    public void onStop() {
        mNetworkBus.unregister(this);
    }

    @Subscribe
    public void onNewsItemsReceived(@NonNull Events.NewsItemsSuccessEvent event) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showNewsItems(event.getData());
    }

    @Subscribe
    public void onRetrieveNewsItemsFailed(@NonNull Events.NewsItemsRequestFailedEvent failed) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showError();
    }
}

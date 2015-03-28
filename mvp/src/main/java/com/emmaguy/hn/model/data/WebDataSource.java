package com.emmaguy.hn.model.data;

import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.comments.OnCommentErrorListener;
import com.emmaguy.hn.model.data.comments.OnCommentNextListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemErrorListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemNextListener;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by emma on 21/03/15.
 */
public class WebDataSource implements HackerNewsDataSource {
    private static final int MAX_NUMBER_STORIES = 1;
    private static final String ENDPOINT_URL_HACKER_NEWS_API = "https://hacker-news.firebaseio.com";
    private static HackerNewsDataSource sDataSourceInstance = null;
    private final HackerNewsService mHackerNewsService;

    private WebDataSource() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT_URL_HACKER_NEWS_API)
                .build();

        mHackerNewsService = restAdapter.create(HackerNewsService.class);
    }

    public static HackerNewsDataSource getInstance() {
        if (sDataSourceInstance == null) {
            sDataSourceInstance = new WebDataSource();
        }

        return sDataSourceInstance;
    }

    private static <T> Observable.Operator<T, List<T>> flattenList() {
        return new Observable.Operator<T, List<T>>() {
            @Override
            public Subscriber<? super List<T>> call(final Subscriber<? super T> subscriber) {
                return new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(List<T> list) {
                        for (T c : list) {
                            subscriber.onNext(c);
                        }
                    }
                };
            }
        };
    }

    @Override
    public void getLatestNewsItems() {
        createLatestNewsItemsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnListNewsItemNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnListNewsItemErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    private Observable<List<NewsItem>> createLatestNewsItemsObservable() {
        return mHackerNewsService.topStories()
                .lift(this.<String>flattenList())
                .limit(MAX_NUMBER_STORIES)
                .flatMap(new Func1<String, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(String id) {
                        return mHackerNewsService.item(id);
                    }
                }).toList();
    }

    @Override
    public void getComments(List<String> ids) {
        createCommentsObservable(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnCommentNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnCommentErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    private Observable<List<Comment>> createCommentsObservable(List<String> ids) {
        return Observable.from(ids)
                .flatMap(new Func1<String, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(String id) {
                        return mHackerNewsService.comment(id);
                    }
                }).toList();
    }
}

package com.emmaguy.hn.comments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.emmaguy.hn.R;
import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.WebDataSource;
import com.emmaguy.hn.model.data.HackerNewsDataSource;
import com.emmaguy.hn.presenter.comments.CommentsPresenter;
import com.emmaguy.hn.presenter.comments.CommentsPresenterImpl;
import com.emmaguy.hn.view.CommentsView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentsActivity extends ActionBarActivity implements CommentsView {
    public static final String EXTRA_NEWS_ITEM_KEY_ID = "key_news_item_id";

    @InjectView(R.id.activity_news_item_comments_root) ViewGroup mRoot;
    @InjectView(R.id.comments_progress_bar_loading) ProgressBar mLoadingIndicator;

    private CommentsPresenter mPresenter;
    private HackerNewsDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);

        ButterKnife.inject(this);

        ArrayList<String> ids = getIntent().getStringArrayListExtra(EXTRA_NEWS_ITEM_KEY_ID);

        mDataSource = WebDataSource.getInstance();
        mPresenter = new CommentsPresenterImpl(this,
                ids,
                mDataSource,
                EventBusProvider.getNetworkBusInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();

        super.onStop();
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showComments(ArrayList<Comment> comments) {
        TreeNode root = TreeNode.root();

        for (Comment c : comments) {
            TreeNode parent = new TreeNode(c).setViewHolder(new CommentTreeViewHolder(this));
            root.addChild(parent);
        }

        int horizontal = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        int vertical = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);

        AndroidTreeView treeView = new AndroidTreeView(this, root);
        ScrollView view = (ScrollView) treeView.getView();
        view.setClipToPadding(false);
        view.setVerticalScrollBarEnabled(false);
        view.setPadding(horizontal, vertical, horizontal, vertical);
        mRoot.addView(view);
    }
}

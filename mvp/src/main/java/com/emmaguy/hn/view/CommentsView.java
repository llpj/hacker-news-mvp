package com.emmaguy.hn.view;

import com.emmaguy.hn.model.Comment;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public interface CommentsView {
    void showLoadingIndicator();
    void hideLoadingIndicator();

    void showComments(ArrayList<Comment> comments);
}

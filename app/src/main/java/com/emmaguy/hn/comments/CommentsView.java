package com.emmaguy.hn.comments;

import com.emmaguy.hn.MVPView;
import com.emmaguy.hn.model.Comment;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public interface CommentsView extends MVPView {
    void showLoadingIndicator();
    void hideLoadingIndicator();

    void showComments(ArrayList<Comment> comments);
}

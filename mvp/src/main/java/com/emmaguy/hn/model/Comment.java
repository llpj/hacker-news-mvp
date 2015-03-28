package com.emmaguy.hn.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public class Comment {
    @SerializedName("text")
    private String mText = "";

    @SerializedName("kids")
    private ArrayList<String> mCommentIds = new ArrayList<>();

    public Comment() {}

    public Comment(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public ArrayList<String> getCommentIds() {
        return mCommentIds;
    }
}

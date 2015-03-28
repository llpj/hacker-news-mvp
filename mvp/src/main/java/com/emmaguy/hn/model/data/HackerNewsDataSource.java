package com.emmaguy.hn.model.data;

import java.util.List;

public interface HackerNewsDataSource {
    void getLatestNewsItems();
    void getComments(List<String> ids);
}

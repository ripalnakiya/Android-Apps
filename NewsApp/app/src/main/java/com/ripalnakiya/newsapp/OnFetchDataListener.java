package com.ripalnakiya.newsapp;

import com.ripalnakiya.newsapp.models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(String message);
}
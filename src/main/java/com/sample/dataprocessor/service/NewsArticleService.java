package com.sample.dataprocessor.service;


import com.sample.dataprocessor.entity.NewsArticle;

import java.util.List;

public interface NewsArticleService {
    List<NewsArticle> getNewsData(Integer limit);

    Long deleteNewsData(String name);
}

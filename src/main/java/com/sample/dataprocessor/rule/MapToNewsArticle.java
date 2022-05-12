package com.sample.dataprocessor.rule;



import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.entity.NewsArticle;

import java.util.function.Function;

/**
 * Rule function to map {@link com.sample.dataprocessor.dto.Article} to {@link com.sample.dataprocessor.entity.NewsArticle}
 */
public class MapToNewsArticle implements Function<Article, NewsArticle> {
    @Override
    public NewsArticle apply(Article article) {
        return new NewsArticle(article);
    }
}

package com.sample.dataprocessor.rule;


import com.sample.dataprocessor.dto.Article;

import java.util.function.Function;

import static com.sample.dataprocessor.util.Constants.MAX_DESCRIPTION_LENGTH;

/**
 * Rule function to trim description length to 250 characters
 */
public class TrimDescriptionRule implements Function<Article, Article> {
    @Override
    public Article apply(Article article) {
        if (article.getDescription() != null && article.getDescription().length() > MAX_DESCRIPTION_LENGTH)
            article.setDescription(article.getDescription().substring(0, MAX_DESCRIPTION_LENGTH));
        return article;
    }
}

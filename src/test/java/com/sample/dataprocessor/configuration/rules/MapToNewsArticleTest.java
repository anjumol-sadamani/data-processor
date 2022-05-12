package com.sample.dataprocessor.configuration.rules;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.Source;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MapToNewsArticleTest {

    @Test
    void apply() {
        var article = new Article(
                new Source("sourceId", "sourceName"),
                "author", "title", "description", new Date(),"content"
                );
        MapToNewsArticle mapToNewsArticle = new MapToNewsArticle();
        var newsArticle = mapToNewsArticle.apply(article);

        assertEquals("sourceId", newsArticle.getSourceId());
    }
}
package com.sample.dataprocessor.configuration.rules;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.Source;
import com.sample.dataprocessor.rule.MapToNewsArticle;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MapToNewsArticleTest {

    @Test
    void apply() {
        var article = new Article(
                new Source("sourceId", "sourceName"),
                "author", "title", "description", LocalDateTime.now(),"content"
                );
        MapToNewsArticle mapToNewsArticle = new MapToNewsArticle();
        var newsArticle = mapToNewsArticle.apply(article);

        assertEquals("sourceId", newsArticle.getSourceId());
    }
}
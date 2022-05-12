package com.sample.dataprocessor.configuration.rules;

import com.sample.dataprocessor.dto.Article;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddSourceRuleTest {

    @Test
    void apply() {
        var article = new Article(
                null,
                "author", "title", "description", new Date(),"content"
        );
        var addSourceRule = new AddSourceRule();
        var actualArticle = addSourceRule.apply(article);

        assertNotNull(actualArticle.getSource());
        assertEquals("data-processor-app", actualArticle.getSource().getId());
    }
}
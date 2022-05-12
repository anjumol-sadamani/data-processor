package com.sample.dataprocessor.configuration.rules;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.rule.AddSourceRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddSourceRuleTest {

    @Test
    void apply() {
        var article = new Article(
                null,
                "author", "title", "description", LocalDateTime.now(),"content"
        );
        var addSourceRule = new AddSourceRule();
        var actualArticle = addSourceRule.apply(article);

        assertNotNull(actualArticle.getSource());
        assertEquals("data-processor-app", actualArticle.getSource().getId());
    }
}
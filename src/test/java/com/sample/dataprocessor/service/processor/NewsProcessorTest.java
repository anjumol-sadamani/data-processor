package com.sample.dataprocessor.service.processor;

import com.sample.dataprocessor.configuration.rules.AddSourceRule;
import com.sample.dataprocessor.configuration.rules.MapToNewsArticle;
import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.repo.NewsArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class NewsProcessorTest {

    @Mock
    NewsArticleRepository articleRepository;
    @InjectMocks
    NewsProcessor newsProcessor;


    @Test
    void process() {
        Function<Article, NewsArticle> rules = new AddSourceRule().andThen(new MapToNewsArticle());

        var articles = List.of(new Article(null, "author", "title",
                "description", null, "content"));
        var news = new News("ok", 2, articles);
        NewsArticle newsArticle = new NewsArticle("data-processor-app", "Data-processor App", "author",
                "title", "description", "content", LocalDateTime.now());
        ArrayList<NewsArticle> expectedList = new ArrayList<>();
        expectedList.add(newsArticle);
        newsProcessor.processNews(news, rules);
        Mockito.verify(articleRepository, Mockito.times(1)).saveAll(Mockito.any());
    }


}
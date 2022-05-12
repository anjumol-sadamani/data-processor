package com.sample.dataprocessor.service.processor;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.repo.NewsArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service to apply a processing logic to {@link News} object
 */
@Service
public class NewsProcessor implements Processor<News, Article, NewsArticle> {
    Logger logger = LoggerFactory.getLogger(NewsProcessor.class);
    private final NewsArticleRepository articleRepository;
    @Autowired
    public NewsProcessor(NewsArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void processNews(News news, Function<Article, NewsArticle> rules) {
        List<NewsArticle> articles =
                news.getArticles()
                        .parallelStream()
                        .map(rules)
                        .collect(Collectors.toList());
        logger.info("Processing completed for {} records", articles.size());
        articleRepository.saveAll(articles);
    }
}

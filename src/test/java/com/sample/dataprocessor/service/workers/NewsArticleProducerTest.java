package com.sample.dataprocessor.service.workers;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.service.collector.DataCollector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsArticleProducerTest {

    @Mock
    private DataCollector dataCollector;
    @Mock
    private BlockingQueue<News> dataQueue;
    @InjectMocks
    private NewsArticleProducer articleProducer = new NewsArticleProducer();

    @BeforeAll
    public void setUp() {
        ReflectionTestUtils.setField(articleProducer, "endpoint", "endpoint %s");
        ReflectionTestUtils.setField(articleProducer, "key", "key");
    }
    @Test
    void runTest() throws InterruptedException {
        var articles = List.of(new Article(null, "author", "title",
                "description", LocalDateTime.now(), "content"));
        var news = new News("ok", 2, articles);
        Mockito.when(dataCollector.fetchData(eq("endpoint key"), Mockito.any())).thenReturn(news);

        articleProducer.run();
        Mockito.verify(dataQueue, Mockito.times(1)).put(news);
    }

    @Test
    void validResponseFailureOnNull() throws InterruptedException {
        Mockito.when(dataCollector.fetchData(eq("endpoint key"), Mockito.any())).thenReturn(null);

        articleProducer.run();
        Mockito.verify(dataQueue, Mockito.never()).put(Mockito.any());
    }

    @Test
    void validResponseFailureOnNoArticles() throws InterruptedException {
        var articles = new ArrayList<Article>();
        var news = new News("error", 0, articles);
        Mockito.when(dataCollector.fetchData(eq("endpoint key"), Mockito.any())).thenReturn(news);

        articleProducer.run();
        Mockito.verify(dataQueue, Mockito.never()).put(news);
    }
}
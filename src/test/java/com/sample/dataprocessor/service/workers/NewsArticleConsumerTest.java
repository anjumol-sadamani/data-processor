package com.sample.dataprocessor.service.workers;

import com.sample.dataprocessor.configuration.Constants;
import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.service.processor.Processor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class NewsArticleConsumerTest {

    @Mock
    private BlockingQueue<News> dataQueue;
    @Mock
    private Processor processor;
    @InjectMocks
    private NewsArticleConsumer articleConsumer;

    @Test
    void run() throws InterruptedException {
        var date = new Date();
        var articles = List.of(new Article(null, "author", "title",
                "description", date, "content"));
        var news = new News("ok", 2, articles);

        Mockito.when(dataQueue.take()).thenReturn(news).thenReturn(new News(Constants.POISON, 0, null));

        articleConsumer.run();
        Mockito.verify(processor, Mockito.times(1)).processNews(Mockito.eq(news), Mockito.any(Function.class));

    }
}
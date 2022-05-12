package com.sample.dataprocessor.service.workers;


import com.sample.dataprocessor.configuration.Constants;
import com.sample.dataprocessor.configuration.rules.AddSourceRule;
import com.sample.dataprocessor.configuration.rules.MapToNewsArticle;
import com.sample.dataprocessor.configuration.rules.TrimDescriptionRule;
import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.service.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

/**
 * Runnable task for consuming {@link com.sample.dataprocessor.dto.News} from the Queue.
 * Rules are build and passed to {@link com.sample.dataprocessor.service.processor.NewsProcessor#processNews(News, Function)}
 * along with the {@link com.sample.dataprocessor.dto.News} object
 */
@Component
@Scope("prototype")
public class NewsArticleConsumer implements Runnable {
    Logger logger = LoggerFactory.getLogger(NewsArticleConsumer.class);

    // Building rules for applying to data
    private Function<Article, NewsArticle> rules =
            new TrimDescriptionRule()
                    .andThen(new AddSourceRule())
                    .andThen(new MapToNewsArticle());

    @Autowired
    private BlockingQueue<News> dataQueue;

    @Autowired
    private Processor<News, Article, NewsArticle> processor;

    @Override
    public void run() {

        while (true) {
            News news = null;
            try {
                 news = dataQueue.take();
                logger.info("Consumer thread {} received data | Status {} Articles {}",
                        Thread.currentThread().getName(), news.getStatus(), news.getTotalResults());

                if (Constants.POISON.equals(news.getStatus())) break;

                processor.processNews(news, rules);
            } catch (InterruptedException e) {
                logger.error("Consumer InterruptedException thread : {} | {}", Thread.currentThread().getName(), e.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.info(String.valueOf(news));
                logger.error("Consumer exception thread : {} | {}", Thread.currentThread().getName(), e);
            }

        }
        logger.warn("Consumer thread {} exiting on poison", Thread.currentThread().getName());
    }
}

package com.sample.dataprocessor.service.workers;

import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.service.collector.DataCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;

/**
 * Runnable task for producing {@link com.sample.dataprocessor.dto.News}
 * News objects are fetched using {@link DataCollector} and inserted to Queue
 */
@Component
@Scope("prototype")
public class NewsArticleProducer implements Runnable {

    Logger logger = LoggerFactory.getLogger(NewsArticleProducer.class);

    @Value("${api.uri}")
    private String endpoint;
    @Value("${api.key}")
    private String key;

    @Autowired
    private DataCollector dataCollector;
    @Autowired
    private BlockingQueue<News> dataQueue;

    @Override
    public void run() {
        String uri = String.format(endpoint, key);
        logger.info("Scheduler called thread : {} @ {}", Thread.currentThread().getName(), Instant.now());
        News response = dataCollector.fetchData(uri, new ParameterizedTypeReference<>() {
        });

        if (response == null || response.getArticles() == null || response.getArticles().isEmpty()){
            return;
        }

        logger.info("Queue Size - {} ", dataQueue.size());
        try {
            dataQueue.put(response);
        } catch (InterruptedException e) {
            logger.error("Queue Insert interrupted: QSize - {} | ThreadName - {}", dataQueue.size(), Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}

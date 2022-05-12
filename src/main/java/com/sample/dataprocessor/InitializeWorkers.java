package com.sample.dataprocessor;


import com.sample.dataprocessor.service.workers.NewsArticleConsumer;
import com.sample.dataprocessor.service.workers.NewsArticleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sample.dataprocessor.configuration.Constants.INITIAL_DELAY;

/**
 * Initializer class for the producers and consumer
 */
@Component
public class InitializeWorkers implements ApplicationRunner {

    @Value("${scheduler.interval}")
    private Integer interval;
    @Autowired
    private NewsArticleConsumer newsArticleConsumer;
    @Autowired
    private NewsArticleProducer newsArticleProducer;
    @Autowired
    @Qualifier("scheduledThreadPool")
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    @Qualifier("singleThreadPool")
    private ExecutorService singleExecutorService;

    @Override
    public void run(ApplicationArguments args) {
        scheduledExecutorService.scheduleAtFixedRate(newsArticleProducer, INITIAL_DELAY, interval, TimeUnit.SECONDS);
        singleExecutorService.submit(newsArticleConsumer);
        singleExecutorService.submit(()-> scheduledExecutorService.shutdownNow());
    }
}

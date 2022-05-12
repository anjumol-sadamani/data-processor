package com.sample.dataprocessor.configuration;


import com.sample.dataprocessor.dto.News;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.*;

import static com.sample.dataprocessor.util.Constants.QUEUE_SIZE;
import static com.sample.dataprocessor.util.Constants.SCHEDULED_POOL_SIZE;


@Configuration
public class Config {

    @Value("${request.time.out}")
    private int timeOut;

    @Value("${base.url}")
    private String baseUrl;

    @Bean
    public BlockingQueue<News> getDataQueue() {
        return new ArrayBlockingQueue<>(QUEUE_SIZE);
    }

    @Bean
    @Qualifier("scheduledThreadPool")
    public ScheduledExecutorService scheduledThreadPool() {
            return Executors.newScheduledThreadPool(SCHEDULED_POOL_SIZE);
    }

    @Bean
    @Qualifier("singleThreadPool")
    public ExecutorService fixedThreadPool() {

        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public WebClient webClient(){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeOut)
                .responseTimeout(Duration.ofMillis(timeOut))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeOut, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(timeOut, TimeUnit.MILLISECONDS)));


        return WebClient.builder()
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();
    }
}

package com.sample.dataprocessor.service.collector;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpDataCollectorTest {
    private static MockWebServer mockWebServer;
    private HttpDataCollector httpDataCollector;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());
      httpDataCollector = new HttpDataCollector(WebClient.builder().baseUrl(baseUrl).build());
    }

    @Test
    void fetchData_success() throws IOException{
        Article article = new Article();
        News news = new News();
        news.setArticles(Arrays.asList(article));
        news.setStatus("ok");
        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(news))
                .addHeader("Content-Type", "application/json"));

        News news1 = httpDataCollector.fetchData("/news/articles?limit=30", new ParameterizedTypeReference<News>() {});
        assertEquals(news.getStatus(), news1.getStatus());
    }

}
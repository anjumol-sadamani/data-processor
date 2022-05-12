package com.sample.dataprocessor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.News;
import com.sample.dataprocessor.dto.Source;
import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.repo.NewsArticleRepository;
import com.sample.dataprocessor.service.NewsArticleService;
import com.sample.dataprocessor.service.collector.HttpDataCollector;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DataProcessorApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIntegrationTests {
	private static MockWebServer mockWebServer;

	@Autowired
	private NewsArticleRepository articleRepository;
	@Autowired
	private BlockingQueue<News> dataQueue;
	@Autowired
	private NewsArticleService newsArticleService;

	@Autowired
	private TestRestTemplate restTemplate;

	private String URL = "/news/articles?source_name=source";

	@BeforeAll
	static void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start(8081);
	}

	@AfterAll
	static void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	void integrationTest() throws InterruptedException, JsonProcessingException {
		Source source = new Source("1", "source");
		Article article = new Article(source, "author", "title", "description", null, "content");
		News news = new News();
		news.setArticles(Arrays.asList(article));
		news.setStatus("ok");

		mockWebServer
				.enqueue(new MockResponse()
						.setBody(new ObjectMapper().writeValueAsString(news))
						.addHeader("Content-Type", "application/json"));

		Thread.sleep(5000);
		ResponseEntity<List<NewsArticle>> responseEntity =
				restTemplate.exchange(
						URL,
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<NewsArticle>>() {}
				);
		var actualResp = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(actualResp.size() > 0);
		assertEquals("source", actualResp.get(0).getSourceName());
	}
}

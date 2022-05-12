package com.sample.dataprocessor;


import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.service.NewsArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = { "scheduler.interval=1" })
class ApplicationIntegrationTests {

	@Autowired
	private NewsArticleService apiService;

	@Test
	void integrationTest() throws InterruptedException {
		// Waiting for the application to complete initialisation (fetch and load data)
		// interval override to 1 second
		Thread.sleep(5000);

		List<NewsArticle> newsArticles = apiService.getNewsData(10);

		System.out.println(newsArticles);
		assertTrue(newsArticles.size() > 0);
	}

}

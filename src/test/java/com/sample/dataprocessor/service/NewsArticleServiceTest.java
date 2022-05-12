package com.sample.dataprocessor.service;

import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.repo.NewsArticleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsArticleServiceTest {

    @Mock
    private NewsArticleRepository articleRepository;
    @InjectMocks
    private NewsArticleServiceImpl articleService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(articleService, "defaultRowCount", 5);
    }

    @Test
    void getNewsData() {
        var expectedList = List.of(new NewsArticle(1l,"demo-app","Demo App", "author",
                "title","description", "content",  LocalDateTime.now()));
        var pageable = PageRequest.of(0, 3);
        Mockito.when(articleRepository.findAllByOrderByCreatedDateDesc(pageable)).thenReturn(expectedList);

        var newsArticles = articleService.getNewsData(3);

        assertEquals(1, newsArticles.size());
    }

    @Test
    void getNewsDataDefaultLimit() {
        List<NewsArticle> expectedList = new ArrayList<>();
        var pageable = PageRequest.of(0, 5);
        Mockito.when(articleRepository.findAllByOrderByCreatedDateDesc(pageable)).thenReturn(expectedList);

        articleService.getNewsData(null);
        Mockito.verify(articleRepository, Mockito.times(1)).findAllByOrderByCreatedDateDesc(pageable);
    }

    @Test
    void deleteNewsData() {
        Mockito.when(articleRepository.deleteAllBySourceName(Mockito.anyString())).thenReturn(10L);

        long deleteCount = articleService.deleteNewsData("");

        Mockito.verify(articleRepository, Mockito.times(1)).deleteAllBySourceName("");
        assertEquals(10, deleteCount);
    }

}
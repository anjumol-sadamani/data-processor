package com.sample.dataprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.service.NewsArticleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsArticleService newsArticleService;

    @Test
    void getArticles() throws Exception {
        var expectedList = List.of(new NewsArticle(1l,"data-processor-app","BBC", "author",
                "title","description", "content", null));

        Mockito.when(newsArticleService.getNewsData(10)).thenReturn(expectedList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/news/articles?limit=10"))
                .andExpect(status().isOk()).andReturn();
        var actualList = mvcResult.getResponse().getContentAsString();

        Mockito.verify(newsArticleService, Mockito.times(1)).getNewsData(10);
        assertEquals(new ObjectMapper().writeValueAsString(expectedList),actualList);
    }

    @Test
    void testDeleteArticlesBadRequest() throws Exception {
        Mockito.when(newsArticleService.deleteNewsData("")).thenReturn(1l);

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/articles?source_name="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteArticles() throws Exception {
        Mockito.when(newsArticleService.deleteNewsData("something")).thenReturn(1l);

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/articles?source_name=something"))
                .andExpect(status().isAccepted());
        Mockito.verify(newsArticleService, Mockito.times(1)).deleteNewsData("something");
    }
}
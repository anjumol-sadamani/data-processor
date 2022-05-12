package com.sample.dataprocessor.controller;

import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.exception.BadRequestException;
import com.sample.dataprocessor.service.NewsArticleService;
import com.sample.dataprocessor.util.Constants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsArticleService newsArticleService;

    @Autowired
    public NewsController(NewsArticleService apiService){
        this.newsArticleService = apiService;
    }

    /**
     * API to fetch latest 'N' records
     *
     * @param limit limit for the number of records in response
     * @return List of {@link }
     */
    @GetMapping(value = "/articles")
    public ResponseEntity<List<NewsArticle>> getArticles(@RequestParam(name = Constants.PARAM_LIMIT, required = false) Integer limit) {
        List<NewsArticle> articles = newsArticleService.getNewsData(limit);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * API to delete records by source name
     */
    @DeleteMapping(value = "/articles")
    public ResponseEntity<String> deleteArticles(@RequestParam(name = Constants.PARAM_SOURCE) String sourceName) {
        if(Strings.isBlank(sourceName))
            throw new BadRequestException("Source name can not be blank");
        Long affectedRowsCount = newsArticleService.deleteNewsData(sourceName);
        String response = "No records found to be deleted";
        if (affectedRowsCount != 0)
            response = "Deleted " + affectedRowsCount + " records";
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}

package com.sample.dataprocessor.service;

import com.sample.dataprocessor.entity.NewsArticle;
import com.sample.dataprocessor.repo.NewsArticleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    @Value("${fetch.count.default}")
    private int defaultRowCount;

    @Autowired
    private NewsArticleRepository articleRepository;

    @Override
    public List<NewsArticle> getNewsData(Integer limit) {
        if (limit == null || limit <= 0)
            limit = defaultRowCount;
        Pageable pageable = PageRequest.of(0, limit);
        return articleRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    @Transactional
    public Long deleteNewsData(@NonNull String name) {
       return articleRepository.deleteAllBySourceName(name);
    }
}

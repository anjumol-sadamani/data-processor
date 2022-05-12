package com.sample.dataprocessor.repo;

import com.sample.dataprocessor.entity.NewsArticle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepository extends PagingAndSortingRepository<NewsArticle, Long> {

    List<NewsArticle> findAllByOrderByCreatedDateDesc(Pageable pageable);
    Long deleteAllBySourceName(String name);
}
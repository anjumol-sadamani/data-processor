package com.sample.dataprocessor.rule;

import com.sample.dataprocessor.dto.Article;
import com.sample.dataprocessor.dto.Source;
import org.apache.logging.log4j.util.Strings;

import java.util.function.Function;

/**
 * Rule function to add source to Article if no source found
 */
public class AddSourceRule implements Function<Article, Article> {
    @Override
    public Article apply(Article article) {
        Source source = article.getSource();
        if(source == null || Strings.isBlank(source.getId()) || Strings.isBlank(source.getName())){
            article.setSource(new Source("data-processor-app","Data-processor App"));
        }
        return article;
    }
}

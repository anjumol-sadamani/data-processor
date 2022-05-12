package com.sample.dataprocessor.entity;

import com.sample.dataprocessor.dto.Article;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String sourceId;
    private String sourceName;
    private String author;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdDate;

    public NewsArticle(@NonNull Article article) {
        if(Objects.nonNull(article.getSource())){
            this.sourceId = article.getSource().getId();
            this.sourceName = article.getSource().getName();
        }
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.content = article.getContent();
        this.createdDate = LocalDateTime.now();
    }

}
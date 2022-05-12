package com.sample.dataprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Source source;
    private String author;
    private String title;
    private String description;
    private LocalDateTime publishedAt;
    private String content;
}

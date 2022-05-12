package com.sample.dataprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Source source;
    private String author;
    private String title;
    private String description;
    private Date publishedAt;
    private String content;
}

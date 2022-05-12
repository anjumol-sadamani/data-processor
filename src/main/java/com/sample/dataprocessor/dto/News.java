package com.sample.dataprocessor.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class News {
    private String status;
    private int totalResults;
    private List<Article> articles;
}

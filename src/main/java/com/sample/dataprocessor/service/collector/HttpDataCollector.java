package com.sample.dataprocessor.service.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HttpDataCollector implements DataCollector {

    Logger logger = LoggerFactory.getLogger(HttpDataCollector.class);

    private final WebClient webClient;

    @Autowired
    public HttpDataCollector(WebClient webClient){
       this.webClient = webClient;
    }

    @Override
    public <T> T fetchData(String uri, ParameterizedTypeReference<T> typeReference) {
         return webClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(typeReference)
                .block();
    }
}

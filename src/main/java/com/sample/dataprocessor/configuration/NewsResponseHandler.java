package com.sample.dataprocessor.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dataprocessor.dto.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Class to de-serialize News from JSON to Java Object
 */
public class NewsResponseHandler implements HttpResponse.BodyHandler<News> {
    Logger logger = LoggerFactory.getLogger(NewsResponseHandler.class);

    @Override
    public HttpResponse.BodySubscriber<News> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<String> bodySubscriber = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(bodySubscriber, body -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(body, News.class);
            } catch (IOException e) {
                logger.error("Error occurred during data fetch from API {} \n {}", e.getMessage(), e.getCause());
                return null;
            }
        });
    }
}

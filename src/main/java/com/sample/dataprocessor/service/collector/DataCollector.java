package com.sample.dataprocessor.service.collector;


import org.springframework.core.ParameterizedTypeReference;

/**
 * Interface for fetching data from an external source.
 */
public interface DataCollector {
    /**
     * Method to fetch data from an external API
     * @param endpoint Endpoint of the API
     * @param typeReference Expected response type
     * @return {@link T}
     */
    <T> T fetchData(String endpoint, ParameterizedTypeReference<T> typeReference);
}

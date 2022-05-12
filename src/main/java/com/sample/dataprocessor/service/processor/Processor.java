package com.sample.dataprocessor.service.processor;

import java.util.function.Function;

/**
 * Class to process an input data with set of given rules
 * @param <T> Source type of data
 * @param <U> Input type of data for applying rules
 * @param <V> Output type of data from rule applied
 */
public interface Processor<T, U, V> {
    /**
     * Method to apply set of rules to source data
     * @param source
     * @param rules
     */
    void processNews(T source, Function<U, V> rules);
}

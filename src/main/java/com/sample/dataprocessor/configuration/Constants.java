package com.sample.dataprocessor.configuration;

public final class Constants {
    public static final String POISON = "POISON";

    public static final int MAX_DESCRIPTION_LENGTH = 250;
    public static final int QUEUE_SIZE = 1000;
    public static final int SCHEDULED_POOL_SIZE = 10;
    public static final int INITIAL_DELAY = 0;

    public enum NewsStatus {
        OK("ok"), ERROR("error");

        private String value;

        NewsStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private Constants() {
    }
}

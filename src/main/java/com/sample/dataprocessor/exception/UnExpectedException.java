package com.sample.dataprocessor.exception;

public class UnExpectedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String logMessage;

    public UnExpectedException(String msg) {
        super(msg);
    }

    public UnExpectedException(String msg,String logMessage, Throwable throwable) {
        super(msg, throwable);
        this.logMessage = logMessage;
    }

    public String getLogMessage(){
        return this.logMessage;
    }
}

package com.polaris.polaris_digitech.exception;

public class WeightLimitExceededException extends RuntimeException {
    public WeightLimitExceededException(String message) {
        super(message);
    }
}
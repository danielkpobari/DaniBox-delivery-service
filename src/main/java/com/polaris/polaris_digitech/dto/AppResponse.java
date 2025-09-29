package com.polaris.polaris_digitech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppResponse<T>(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        T data,
        boolean isSuccess,
        boolean isError,
        String detail,
        ResponseStatus status
) {
    // Internationalized constructors

    public AppResponse(T data) {
        this(LocalDateTime.now(), data, true, false, "Success", ResponseStatus.SUCCESSFUL);
    }

    public AppResponse(T data, boolean success) {
        this(LocalDateTime.now(), data, success, !success,
                success ? "Success" : "Failed",
                success ? ResponseStatus.SUCCESSFUL : ResponseStatus.FAILED);
    }

    public AppResponse(T data, boolean success, String description) {
        this(LocalDateTime.now(), data, success, !success, description,
                success ? ResponseStatus.SUCCESSFUL : ResponseStatus.FAILED);
    }

    public AppResponse(String successDescription, T data) {
        this(LocalDateTime.now(), data, true, false, successDescription, ResponseStatus.SUCCESSFUL);
    }

    public AppResponse(T data, String description) {
        this(LocalDateTime.now(), data, false, true, description, ResponseStatus.FAILED);
    }

    public AppResponse(String description, ResponseStatus status) {
        this(LocalDateTime.now(), null,
                status == ResponseStatus.SUCCESSFUL,
                status == ResponseStatus.FAILED,
                description, status);
    }

    public AppResponse(String description, boolean isSuccess, boolean isError, ResponseStatus responseStatus) {
        this(LocalDateTime.now(), null, isSuccess, isError, description, responseStatus);
    }

    public AppResponse() {
        this(LocalDateTime.now(), null, true, false, "Successful", ResponseStatus.SUCCESSFUL);
    }

    // Internationalization helper methods

    public static <T> AppResponse<T> success(String messageCode, T data) {
        return new AppResponse<>(messageCode, data);
    }

    public static <T> AppResponse<T> success(String messageCode, T data, Object... args) {
        return new AppResponse<>(messageCode, data);
    }

    public static <T> AppResponse<T> error(T data, String messageCode) {
        return new AppResponse<>(data);
    }

    public enum ResponseStatus {
        SUCCESSFUL("successful"),
        FAILED("failed");

        private final String value;

        ResponseStatus(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }

}
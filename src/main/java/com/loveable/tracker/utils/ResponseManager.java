package com.loveable.tracker.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Service
public class ResponseManager<T> {
    public ApiResponse<T> success(T data, HttpStatus status) {
        return new ApiResponse<>(status.value(), "Operation Successfully", true, data, status);
    }

    public ApiResponse<T> error(String errorMessage, HttpStatus status) {
        return new ApiResponse<>(status.value(), errorMessage, false, null, status);
    }
}

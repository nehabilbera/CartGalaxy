package com.example.CartGalaxy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public record ApiResponse<T> (@JsonInclude(JsonInclude.Include.NON_NULL) T data, String message, Date timeStamp, String status) {

    public static <T> ApiResponse<T> success(T data, String message){
        ApiResponse<T> apiSuccess = new ApiResponse<>(data, message, new Date(), "Success");
        return apiSuccess;
    }

    public static <T> ApiResponse<T> error(String message){
        ApiResponse<T> apiError = new ApiResponse<>(null, message, new Date(), "Error");
        return apiError;
    }

}

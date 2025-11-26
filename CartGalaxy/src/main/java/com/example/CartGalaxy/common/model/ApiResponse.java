package com.example.CartGalaxy.common.model;

import java.util.Date;

public record ApiResponse<T> (T data, String message, Date timeStamp, String status) {

    public static <T> ApiResponse<T> success(T data, String message){
        ApiResponse<T> apiSuccess = new ApiResponse<>(data, message, new Date(), "Success");
        return apiSuccess;
    }

    public static <T> ApiResponse<T> error(T data, String message){
        ApiResponse<T> apiError = new ApiResponse<>(data, message, new Date(), "Error");
        return apiError;
    }

}

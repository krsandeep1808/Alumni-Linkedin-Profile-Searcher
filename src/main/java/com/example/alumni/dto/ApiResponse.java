package com.example.alumni.dto;

import java.util.List;

public class ApiResponse<T> {
    
    private String status;
    private T data;
    private String message;

    // Constructors
    public ApiResponse() {}

    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    // Static factory methods
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("success", data, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", null, message);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

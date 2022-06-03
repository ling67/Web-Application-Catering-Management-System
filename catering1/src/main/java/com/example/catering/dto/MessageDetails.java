package com.example.catering.dto;

public class MessageDetails {
    private String message;
    private boolean success;

    public MessageDetails(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public MessageDetails(String message) {
        this.message = message;
    }

    public MessageDetails() {
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}

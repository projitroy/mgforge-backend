package com.mgforge.MGForge;

import java.time.OffsetDateTime;

public class ErrorResponse {

    private final String code;
    private final String message;
    private final int status;
    private final String path;
    private final OffsetDateTime timeStamp;

    public ErrorResponse(String code, String message, int status, String path) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.path = path;
        this.timeStamp = OffsetDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}

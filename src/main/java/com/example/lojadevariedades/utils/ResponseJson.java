package com.example.lojadevariedades.utils;

public class ResponseJson {
    private boolean success;
    private String message;
    private String code;
    private Object data;
    private String path;
    private String timestamp;

    public ResponseJson() {}

    public ResponseJson(boolean success, String message, String code, Object data, String path, String timestamp) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
        this.path = path;
        this.timestamp = timestamp;
    }

    public static ResponseJson ok(Object data) {
        return new ResponseJson(true, null, null, data, null, java.time.OffsetDateTime.now().toString());
    }

    public static ResponseJson ok(String message, Object data) {
        return new ResponseJson(true, message, null, data, null, java.time.OffsetDateTime.now().toString());
    }

    public static ResponseJson error(String message, String code) {
        return new ResponseJson(false, message, code, null, null, java.time.OffsetDateTime.now().toString());
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

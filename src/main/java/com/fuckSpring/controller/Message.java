package com.fuckSpring.controller;

/**
 * Created by upsmart on 17-5-12.
 */
public class Message {

    private String status;
    private String code;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Message() {
    }

    public Message(String status, String code, Object data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }
}

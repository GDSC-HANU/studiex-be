package com.gdsc.studyex.infrastructure.share.controllers;

import java.util.List;

public class ResponseBody {
    public String code;
    public String message;
    public Object data;

    public ResponseBody(String message, String code, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseBody(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseBody(String message, List<Object> data) {
        this.message = message;
        this.data = data;
    }

    public ResponseBody(String message) {
        this.message = message;
    }
}

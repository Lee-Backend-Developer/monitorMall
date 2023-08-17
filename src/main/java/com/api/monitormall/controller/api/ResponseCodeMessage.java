package com.api.monitormall.controller.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseInfo {
    private int code;
    private String message;

    private ResponseInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseInfo badRequest(String message) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value(), message);
    }
}

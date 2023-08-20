package com.api.monitormall.controller.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseCodeMessage {
    private int code;
    private String message;

    private ResponseCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCodeMessage badRequest(String message) {
        return new ResponseCodeMessage(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static ResponseCodeMessage duplication(String message) {
        return new ResponseCodeMessage(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static ResponseCodeMessage notFound(String message) {
        return new ResponseCodeMessage(HttpStatus.NOT_FOUND.value(), message);
    }


}

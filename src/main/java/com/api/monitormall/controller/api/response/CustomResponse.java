package com.api.monitormall.controller.api.response;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

public class CustomResponse {

    private static final CustomResponse instance = new CustomResponse();

    private CustomResponse() {
    }

    public static CustomResponse getInstance() {
        return instance;
    }

    public static ResponseEntity badRequest() {
        return new ResponseEntity(BAD_REQUEST);
    }

    public static ResponseEntity<Object> badRequest(String message) {
        ResponseCodeMessage responseInfo = ResponseCodeMessage.badRequest(message);
        return new ResponseEntity<>(responseInfo, BAD_REQUEST);
    }

    public static ResponseEntity<Object> duplication(String message) {
        ResponseCodeMessage responseInfo = ResponseCodeMessage.duplication(message);
        return new ResponseEntity<>(responseInfo, BAD_REQUEST);
    }


}

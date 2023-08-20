package com.api.monitormall.controller.exception;

import com.api.monitormall.controller.exception.response.CustomResponse;
import com.api.monitormall.exception.MonitorMall;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionController {

    @ExceptionHandler(MonitorMall.class)
    default ResponseEntity exception() {
        CustomResponse response = CustomResponse.getInstance();
        return response.badRequest();
    }

    ResponseEntity duplication();

    ResponseEntity notFound();
}

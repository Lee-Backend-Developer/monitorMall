package com.api.monitormall.controller.api;

import com.api.monitormall.controller.api.response.MallResponse;
import com.api.monitormall.exception.DuplicationMember;
import com.api.monitormall.exception.MemberNotFount;
import com.api.monitormall.exception.MonitorMall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    private MallResponse response = MallResponse.getInstance();

    @ExceptionHandler(MonitorMall.class)
    public ResponseEntity exception() {
        return response.badRequest();
    }

    @ExceptionHandler(DuplicationMember.class)
    public ResponseEntity duplicationMember() {
        return response.duplication("중복된 아이디가 있습니다.");
    }

    @ExceptionHandler(MemberNotFount.class)
    public ResponseEntity memberNotFound() {
        return response.badRequest("회원을 찾을 수 없습니다.");
    }




}

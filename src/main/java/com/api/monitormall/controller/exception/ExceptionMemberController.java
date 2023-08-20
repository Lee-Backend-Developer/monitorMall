package com.api.monitormall.controller.exception;

import com.api.monitormall.controller.MemberApiController;
import com.api.monitormall.controller.exception.response.CustomResponse;
import com.api.monitormall.exception.DuplicationMember;
import com.api.monitormall.exception.MonitorMall;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(basePackageClasses = MemberApiController.class)
public class ExceptionMemberController implements ExceptionController {
    private CustomResponse response = CustomResponse.getInstance();

    @Override
    @ExceptionHandler(DuplicationMember.class)
    public ResponseEntity duplication() {
        return response.duplication("중복된 아이디가 있습니다.");
    }

    @Override
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity notFound() {
        return response.badRequest("회원을 찾을 수 없습니다.");
    }

}

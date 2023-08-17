package com.api.monitormall.controller.api;

import com.api.monitormall.request.MemberEdit;
import com.api.monitormall.request.MemberLogin;
import com.api.monitormall.request.MemberRegister;
import com.api.monitormall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("register")
    public void register(MemberRegister request) {
        memberService.register(request);
    }

    @PostMapping("login")
    public void login(MemberLogin memberLogin) {
        memberService.login(memberLogin);
    }

    @PostMapping("edit")
    public void edit(MemberEdit memberEdit) {
        Long memberId = 0L; // todo 잠시 임시값 나중에 세션으로 변경될 예정
        memberService.edit(memberId, memberEdit);
    }

    @PostMapping("change/password")
    public void changePassword(String changePassword) {
        Long memberId = 0L; // todo 잠시 임시값 나중에 세션으로 변경될 예정

        memberService.passwordChange(memberId, changePassword);
    }
}

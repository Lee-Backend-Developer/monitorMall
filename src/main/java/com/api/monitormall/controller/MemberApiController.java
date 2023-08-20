package com.api.monitormall.controller;

import com.api.monitormall.request.MemberEdit;
import com.api.monitormall.request.MemberLogin;
import com.api.monitormall.request.MemberRegister;
import com.api.monitormall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("register")
    public void register(@RequestBody MemberRegister request) {
        memberService.register(request);
    }

    @GetMapping("login")
    public void login(@RequestBody MemberLogin memberLogin) {
        memberService.login(memberLogin);
    }

    @PutMapping("change/profile")
    public void edit(@RequestBody MemberEdit memberEdit, @SessionAttribute(name = "memberId") Long memberId) {
        memberService.edit(memberId, memberEdit);
    }

    @PutMapping("change/password")
    public void changePassword(@RequestBody String changePassword, @SessionAttribute(name = "memberId") Long memberId) {
        memberService.passwordChange(memberId, changePassword);
    }



}

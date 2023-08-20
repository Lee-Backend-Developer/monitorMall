package com.api.monitormall.service;

import com.api.monitormall.entity.Member;
import com.api.monitormall.exception.DuplicationMember;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.request.MemberEdit;
import com.api.monitormall.request.MemberLogin;
import com.api.monitormall.request.MemberRegister;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @DisplayName("회원가입이 되어야한다")
    @Test
    void register_O() {
        // given
        MemberRegister request = MemberRegister.builder()
                .name("홍길동")
                .loginId("hong1")
                .password("1234")
                .build();

        // when
        memberService.register(request);

        // then
        assertEquals(1, memberRepository.count());
    }

    @DisplayName("중복된 아이디가 있는면 에러가 나와야한다")
    @Test
    void duplication_O() {
        // given
        Member createMember = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(createMember);

        MemberRegister request = MemberRegister.builder()
                .name("홍길동")
                .loginId("hong1")
                .password("1234")
                .build();

        // expected
        String message = assertThrows(DuplicationMember.class, () -> {
            memberService.register(request);
        }).getMessage();

        assertEquals("중복된 아이디가 있습니다.", message);
    }

    @DisplayName("로그인이 되어야한다")
    @Test
    void login_O() {
        // given
        Member createMember = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(createMember);

        // when
        MemberLogin request = MemberLogin.builder()
                .loginId("hong1")
                .password("1234")
                .build();
        Member member = memberService.login(request);

        // then
        assertEquals("hong1", member.getLoginId());
    }

    @DisplayName("회원 수정이 되어야한다")
    @Test
    void edit_O() {
        // given
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(member);

        MemberEdit request = MemberEdit.builder()
                .name("김길동")
                .password("0000")
                .address("서울 어느곳")
                .build();

        // when
        memberService.edit(member.getMemberId(), request);

        // then
        Member findMember = memberRepository.findById(member.getMemberId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(findMember.getName(), "김길동");
        assertEquals(findMember.getPassword(), "0000");
        assertEquals(findMember.getAddress(), "서울 어느곳");
    }

    @DisplayName("비밀번호가 수정이 되어야한다.")
    @Test
    void password_change_O() {
        // given
        String changePass = "change";
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(member);

        // when
        memberService.passwordChange(member.getMemberId(), changePass);

        // then
        Member findMember = memberRepository.findById(member.getMemberId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(changePass, findMember.getPassword());
    }

    @DisplayName("회원이 삭제가 되어야한다.")
    @Test
    void delete_O() {
        // given
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(member);

        // expected
        assertEquals(1, memberRepository.count());
        memberService.deleteMember(member.getMemberId());
        assertTrue(memberRepository.findAll().isEmpty());
    }

}
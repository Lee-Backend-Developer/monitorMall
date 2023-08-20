package com.api.monitormall.controller;

import com.api.monitormall.entity.Member;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.request.MemberEdit;
import com.api.monitormall.request.MemberLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MemberApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입이 되어야한다")
    @Test
    void register_O() throws Exception {
        // given
        Member createMember = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();

        // then
        mockMvc.perform(post("/api/member/register")
                        .content(objectMapper.writeValueAsString(createMember))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("중복회원이 있을경우 에러가 발생이 되어야한다")
    @Test
    void register_X() throws Exception {
        // given
        createMember();

        Member createMember = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();

        // then
        mockMvc.perform(post("/api/member/register")
                        .content(objectMapper.writeValueAsString(createMember))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("로그인이 되어야한다.")
    @Test
    void login_O() throws Exception {
        // given
        createMember();
        MemberLogin login = memberLoginBuilder();

        // then
        mockMvc.perform(get("/api/member/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("없는 회원 일 경우 로그인이 실패되어야함")
    @Test
    void login_X() throws Exception {
        MemberLogin login = memberLoginBuilder();

        // then
        mockMvc.perform(get("/api/member/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("회원이 수정이 되어야한다")
    @Test
    void memberEdit_O() throws Exception {
        // given
        MockHttpSession session = createSession();

        MemberEdit request = MemberEdit.builder()
                .name("김길동")
                .password("0000")
                .address("서울 어느곳")
                .build();

        // then
        mockMvc.perform(put("/api/member/change/profile")
                        .session(session)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("비밀번호가 수정이 되어야한다")
    @Test
    void password_O() throws Exception {
        // given
        createMember();
        MockHttpSession session = createSession();

        String changePassword = "1234";

        // then
        mockMvc.perform(put("/api/member/change/password")
                        .content(changePassword)
                        .contentType(APPLICATION_JSON)
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private MockHttpSession createSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", createMember().getMemberId());
        return session;
    }

    private static MemberLogin memberLoginBuilder() {
        MemberLogin login = MemberLogin.builder()
                .loginId("hong1")
                .password("1234")
                .build();
        return login;
    }

    private Member createMember() {
        Member saveMember = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        return memberRepository.save(saveMember);
    }

}
package com.api.monitormall.service;

import com.api.monitormall.entity.Member;
import com.api.monitormall.exception.MemberNotFount;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.request.MemberLogin;
import com.api.monitormall.request.MemberRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void register(MemberRegister request) {
        Member member = Member.builder()
                .name(request.getName())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();

        memberRepository.save(member);
    }

    public Member login(MemberLogin request) {
        Member member = memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword())
                .orElseThrow(MemberNotFount::new);
        return member;
    }

    public List<Member> members() {
        return memberRepository.findAll();
    }

    @Transactional
    public void edit(Long memberId, MemberEdit request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFount::new);
        member.edit(request);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFount::new);

        memberRepository.delete(member);
    }


}

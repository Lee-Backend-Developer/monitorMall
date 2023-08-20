package com.api.monitormall.service;

import com.api.monitormall.entity.Member;
import com.api.monitormall.exception.DuplicationMember;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.request.MemberEdit;
import com.api.monitormall.request.MemberLogin;
import com.api.monitormall.request.MemberRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void register(MemberRegister request) {
        // 같은 아이디가 있는지 검증
        String loginId = request.getLoginId();
        Optional<Member> findLoginId = memberRepository.findByLoginId(loginId);
        if(findLoginId.isPresent()) {
            throw new DuplicationMember();
        }

        Member member = Member.builder()
                .name(request.getName())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();

        memberRepository.save(member);
    }

    public Member login(MemberLogin request) {
        Member member = memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword())
                .orElseThrow(EntityNotFoundException::new);
        return member;
    }

    public List<Member> members() {
        return memberRepository.findAll();
    }

    @Transactional
    public void edit(Long memberId, MemberEdit request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.edit(request);
    }

    @Transactional
    public void passwordChange(Long memberId, String currentPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.changePassword(currentPassword);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }


}

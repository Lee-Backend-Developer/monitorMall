package com.api.monitormall.repository;

import com.api.monitormall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    Optional<Member> findByLoginId(String loginId);
}

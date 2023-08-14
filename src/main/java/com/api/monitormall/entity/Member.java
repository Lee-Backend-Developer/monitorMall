package com.api.monitormall.entity;

import com.api.monitormall.request.MemberEdit;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    private String loginId;
    private String password;
    private String address;

    @Builder
    public Member(String name, String loginId, String password, String address) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.address = address;
    }

    // === 편의 메소드 == //

    public void edit(MemberEdit edit) {
        this.name = edit.getName();
        this.password = edit.getPassword();
        this.address = edit.getAddress();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

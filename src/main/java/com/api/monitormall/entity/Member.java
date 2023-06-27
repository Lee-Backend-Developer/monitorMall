package com.api.monitormall.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
}

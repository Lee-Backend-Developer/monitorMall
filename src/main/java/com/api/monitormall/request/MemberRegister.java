package com.api.monitormall.request;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberRegister {
    private String name;
    private String loginId;
    private String password;
    private String address;

    @Builder
    public MemberRegister(String name, String loginId, String password, String address) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.address = address;
    }
}

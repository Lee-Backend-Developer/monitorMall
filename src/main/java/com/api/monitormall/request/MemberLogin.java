package com.api.monitormall.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberLogin {
    private String loginId;
    private String password;

    @Builder
    public MemberLogin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}

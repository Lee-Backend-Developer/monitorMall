package com.api.monitormall.service;


import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MemberEdit {
    private String name;
    private String password;
    private String address;

}

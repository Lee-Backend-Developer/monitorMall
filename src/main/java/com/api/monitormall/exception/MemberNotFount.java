package com.api.monitormall.exception;

public class MemberNotFount extends MonitorMall {
    private static final String MESSAGE = "회월을 찾을 수 없습니다.";
    public MemberNotFount() {
        super(MESSAGE);
    }
}

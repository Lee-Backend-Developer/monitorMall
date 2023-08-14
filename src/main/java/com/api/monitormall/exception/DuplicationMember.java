package com.api.monitormall.exception;

public class DuplicationMember extends MonitorMall {
    private static final String MESSAGE = "중복된 아이디가 있습니다.";
    public DuplicationMember() {
        super(MESSAGE);
    }
}

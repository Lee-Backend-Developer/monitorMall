package com.api.monitormall.exception;

public class CartNotFount extends MonitorMall {
    private static final String MESSAGE = "카트을 찾을 수 없습니다.";
    public CartNotFount() {
        super(MESSAGE);
    }
}

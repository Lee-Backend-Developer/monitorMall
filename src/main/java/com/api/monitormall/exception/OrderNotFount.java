package com.api.monitormall.exception;

public class OrderNotFount extends MonitorMall {
    private static final String MESSAGE = "주문을 찾을 수 없습니다.";
    public OrderNotFount() {
        super(MESSAGE);
    }
}

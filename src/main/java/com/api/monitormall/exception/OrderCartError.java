package com.api.monitormall.exception;

public class OrderCartError extends MonitorMall {
    private static final String MESSAGE = "장바구니에 담긴 상품이 없습니다.";
    public OrderCartError() {
        super(MESSAGE);
    }
}

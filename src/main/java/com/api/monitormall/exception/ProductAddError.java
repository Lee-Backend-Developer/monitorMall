package com.api.monitormall.exception;

public class ProductAddError extends MonitorMall {
    private static final String MESSAGE = "현재 상품을 주문에 추가할 수 없습니다.";
    public ProductAddError() {
        super(MESSAGE);
    }
}

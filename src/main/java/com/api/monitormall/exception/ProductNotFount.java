package com.api.monitormall.exception;

public class ProductNotFount extends MonitorMall {
    private static final String MESSAGE = "제품을 찾을 수 없습니다.";
    public ProductNotFount() {
        super(MESSAGE);
    }
}

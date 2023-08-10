package com.api.monitormall.exception;

public class CountError extends MonitorMall {
    public CountError(int count) {
        super("현재 재고 " + count + "개 입니다. 이 보다 많이 상품을 담을수 없습니다.");
    }
}

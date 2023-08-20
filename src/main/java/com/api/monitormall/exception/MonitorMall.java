package com.api.monitormall.exception;

import javax.persistence.EntityNotFoundException;

public class MonitorMall extends RuntimeException {
    public MonitorMall(String message) {
        super(message);
    }

    // todo mapper dao -> domain, 인센셥은 jpa에서 제공 해주는게 있음
}

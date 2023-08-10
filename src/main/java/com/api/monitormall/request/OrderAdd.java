package com.api.monitormall.request;

import com.api.monitormall.entity.Delivery;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderAdd {
    private Long memberId;
    private String deliveryAddress;
    private int totalPrice;
    private String cardNumber;

    @Builder
    public OrderAdd(Long memberId, String deliveryAddress, int totalPrice, String cardNumber) {
        this.memberId = memberId;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.cardNumber = cardNumber;
    }
}

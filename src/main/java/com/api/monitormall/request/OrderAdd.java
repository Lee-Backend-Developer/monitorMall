package com.api.monitormall.request;

import com.api.monitormall.entity.Delivery;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderAdd {
    private Long memberId;
    private List<Long> productIds;
    private String deliveryAddress;
    private int totalPrice;
    private String cardNumber;

    @Builder
    public OrderAdd(Long memberId, List<Long> productIds, String deliveryAddress, int totalPrice, String cardNumber) {
        this.memberId = memberId;
        this.productIds = productIds;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.cardNumber = cardNumber;
    }
}

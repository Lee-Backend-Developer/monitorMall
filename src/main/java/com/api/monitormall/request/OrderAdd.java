package com.api.monitormall.request;

import com.api.monitormall.entity.Delivery;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderAdd {
    private Long memberId;
    private List<Long> productIds;
    private Delivery delivery;
    private String deliveryAddress;
    private int totalPrice;
    private String cardNumber;
    private Boolean isRefunded;

    @Builder
    public OrderAdd(Long memberId, List<Long> productIds, Delivery delivery, String deliveryAddress, int totalPrice, String cardNumber, Boolean isRefunded) {
        this.memberId = memberId;
        this.productIds = productIds;
        this.delivery = delivery;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.cardNumber = cardNumber;
        this.isRefunded = isRefunded;
    }
}

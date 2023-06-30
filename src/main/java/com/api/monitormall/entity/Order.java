package com.api.monitormall.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    /*
    todo 내가 원하는 주문 서비스
    - 주문번호 1번으로 가정하면
    - 주문한 상품들이 1번에 들어가야함
    */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;

    @OneToOne
    private Member member;

    @OneToMany
    private List<Product> product;

    @Enumerated(value = EnumType.STRING)
    private Delivery delivery;

    private String deliveryAddress;
    private int totalPrice;
    private String cardNumber;
    private Boolean isRefunded; // 환불이면 true, 아니면 false

    @Builder
    public Order(Member member, List<Product> product, Delivery delivery, String deliveryAddress, int totalPrice, String cardNumber, Boolean isRefunded) {
        this.member = member;
        this.product = product;
        this.delivery = delivery;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.cardNumber = cardNumber;
        this.isRefunded = isRefunded;
    }

    public void refunded() {
        this.isRefunded = true;
    }
}

package com.api.monitormall.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Orders {

    /*
    todo 내가 원하는 주문 서비스
    - 주문번호 1번으로 가정하면
    - 주문한 상품들이 1번에 들어가야함
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderNumber orderNumber;

    @OneToOne
    private Member member;

    @ManyToOne
    private Product product;

    @Enumerated(value = EnumType.STRING)
    private Delivery delivery = Delivery.SHIPMENT;

    private String deliveryAddress;
    private int totalPrice;
    private String cardNumber;

    private Boolean isRefunded = false; // 환불이면 true, 아니면 false

    @Builder
    public Orders(Member member, Product product, String deliveryAddress, int totalPrice, String cardNumber) {
        this.member = member;
        this.product = product;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.cardNumber = cardNumber;
    }
    public void setOrderNumber(OrderNumber orderNumber) {
        this.orderNumber = orderNumber;
        this.orderNumber.getOrderList().add(this);
    }

    public void refunded() {
        this.isRefunded = true;
    }

}

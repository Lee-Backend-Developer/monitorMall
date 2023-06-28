package com.api.monitormall.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cartId;

    @OneToOne
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    public Cart(Long cartId, Member member, Product product) {
        this.cartId = cartId;
        this.member = member;
        this.product = product;
    }
}

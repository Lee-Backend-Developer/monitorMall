package com.api.monitormall.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    private Member member;

    @OneToOne
    private Product product;

    @Builder
    public Cart(Long cartId, Member member, Product product) {
        this.cartId = cartId;
        this.member = member;
        this.product = product;
    }
}

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

    private int count;

    @Builder
    public Cart(Member member, Product product, int count) {
        this.member = member;
        this.product = product;
        this.count = count;
    }

    //== 편의 메소드 ==
    public void setCount(int count) {
        this.count = count;
    }
}

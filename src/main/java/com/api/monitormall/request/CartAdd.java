package com.api.monitormall.request;

import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CartAdd {
    private Long memberId;
    private List<CartProduct> products = new ArrayList<>();

    public CartAdd(Long memberId, CartProduct... products) {
        this.memberId = memberId;
        Arrays.stream(products).forEach(product -> this.products.add(product));
    }

    public static CartProduct cartProductCreate(Long productId, int count) {
        CartProduct cartProduct = new CartProduct(productId, count);
        return cartProduct;
    }
}

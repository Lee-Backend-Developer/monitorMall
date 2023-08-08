package com.api.monitormall.request;

import com.api.monitormall.entity.Product;
import lombok.Data;

@Data
public class CartProduct {
    private Long productId;
    private int count;

    CartProduct(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }
}

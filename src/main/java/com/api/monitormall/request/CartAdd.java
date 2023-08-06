package com.api.monitormall.request;

import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
public class CartAdd {
    private Long memberId;
    private Long productId;
    private int count;

    @Builder
    public CartAdd(Long memberId, Long productId, int count) {
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
    }
}

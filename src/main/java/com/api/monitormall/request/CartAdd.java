package com.api.monitormall.request;

import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
public class CartAdd {
    private Long memberId;
    private Long productId;

    @Builder
    public CartAdd(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }
}

package com.api.monitormall.repository;

import com.api.monitormall.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CustomCartRepository {
    List<Cart> findCarts(Long memberId);

    Optional<Cart> findCart(Long cartId, Long productId);

    List<Cart> findByMemberId(Long memberId);

}

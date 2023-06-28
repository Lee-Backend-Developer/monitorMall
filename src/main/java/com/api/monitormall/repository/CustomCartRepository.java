package com.api.monitormall.repository;

import com.api.monitormall.entity.Cart;

import java.util.List;

public interface CustomCartRepository {
    List<Cart> findCart(Long memberId);
}

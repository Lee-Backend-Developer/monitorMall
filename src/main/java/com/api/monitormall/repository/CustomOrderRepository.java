package com.api.monitormall.repository;

import com.api.monitormall.entity.Orders;

import java.util.List;

public interface CustomOrderRepository {
    List<Orders> findOrders(Long memberId);
}

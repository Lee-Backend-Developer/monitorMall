package com.api.monitormall.repository;

import com.api.monitormall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomOrderRepository {
    List<Order> findOrders(Long memberId);
}

package com.api.monitormall.repository;

import com.api.monitormall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, CustomOrderRepository {
}

package com.api.monitormall.repository;

import com.api.monitormall.entity.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderNumberRepository extends JpaRepository<OrderNumber, Long> {
}

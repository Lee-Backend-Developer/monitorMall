package com.api.monitormall.repository;

import com.api.monitormall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CustomCartRepository {

}

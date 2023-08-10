package com.api.monitormall.service;

import com.api.monitormall.entity.*;
import com.api.monitormall.exception.*;
import com.api.monitormall.repository.*;
import com.api.monitormall.request.OrderAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderNumberRepository orderNumberRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    // 주문 생성, 주문 조회, 주문 환불
    @Transactional
    public void orderAdd(OrderAdd request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(MemberNotFount::new);

        List<Cart> carts = cartRepository.findByMemberId(request.getMemberId());

        if(carts.size() == 0 ) {
            throw new OrderCartError();
        }
        carts.forEach(cart -> { // 상품 검증
            Product product = cart.getProduct();
            if(cart.getCount() > product.getCount()) {
                throw new CountError(product.getCount());
            }
        });

        OrderNumber orderNumber = new OrderNumber();
        orderNumberRepository.save(orderNumber);

        carts.forEach(cart -> {
            Product product = cart.getProduct();

            Orders order = Orders.builder()
                    .orderNumber(orderNumber)
                    .member(member)
                    .product(product)
                    .deliveryAddress(request.getDeliveryAddress())
                    .totalPrice(product.getPrice())
                    .cardNumber(request.getCardNumber())
                    .build();
            orderRepository.save(order);

            cartRepository.deleteById(cart.getCartId());
        });
    }

    public List<Orders> getOrder(Long memberId) {
        return orderRepository.findOrders(memberId);
    }

    @Transactional
    public void refunded(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFount::new);
        order.refunded();
    }
}

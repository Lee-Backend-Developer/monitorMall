package com.api.monitormall.service;

import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Order;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.MemberNotFount;
import com.api.monitormall.exception.OrderNotFount;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.OrderRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.OrderAdd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 주문 생성, 주문 조회, 주문 환불
    @Transactional
    public void orderAdd(OrderAdd request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(MemberNotFount::new);
        List<Product> products = request.getProductIds()
                .stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(ProductNotFount::new))
                .collect(Collectors.toList());

        Order order = Order.builder()
                .member(member)
                .product(products)
                .delivery(request.getDelivery())
                .totalPrice(request.getTotalPrice())
                .cardNumber(request.getCardNumber())
                .isRefunded(request.getIsRefunded())
                .build();
        orderRepository.save(order);
    }

    public List<Order> getOrder(Long memberId) {
        return orderRepository.findOrders(memberId);
    }

    @Transactional
    public void refunded(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFount::new);
        order.refunded();
    }
}

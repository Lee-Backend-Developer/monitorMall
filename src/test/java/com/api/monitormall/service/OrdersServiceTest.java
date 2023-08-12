package com.api.monitormall.service;

import com.api.monitormall.entity.*;
import com.api.monitormall.repository.*;
import com.api.monitormall.request.OrderAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrdersServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderNumberRepository orderNumberRepository;
    @Autowired
    private OrderService orderService;

    @BeforeEach
    void create() {
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        memberRepository.save(member);

        Product product = Product.builder()
                .name("테스트 27인치")
                .price(300000)
                .count(10)
                .brand("dell")
                .inch(27)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product.jpg")
                .build();
        Product product2 = Product.builder()
                .name("테스트 32인치")
                .count(10)
                .price(600000)
                .brand("LG")
                .inch(32)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/LG_product.jpg")
                .build();

        productRepository.save(product);
        productRepository.save(product2);
    }

    @AfterEach
    void delete() {
        orderRepository.deleteAll();
        orderNumberRepository.deleteAll();
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("하나의 주문이 되어야한다.")
    @Test
    void orderAdd_O() {
        // given
        Member member = getMember();

        Product product = getProduct();
        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(1)
                .build();
        cartRepository.save(cart);

        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .cardNumber("1234-1234-1234-1234")
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(1, orderRepository.findOrders(member.getMemberId()).size());
        assertEquals(cartRepository.findByMemberId(member.getMemberId()).size(), 0);
    }

    @DisplayName("여러개의 주문이 되어야한다.")
    @Test
    void orderAdds_O() {
        // given
        Member member = getMember();
        Product product = getProduct();
        Product product2 = getProduct2();

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(1)
                .build();
        cartRepository.save(cart);

        Cart cart2 = Cart.builder()
                .member(member)
                .product(product2)
                .count(1)
                .build();
        cartRepository.save(cart2);

        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .cardNumber("1234-1234-1234-1234")
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(2, orderRepository.findOrders(member.getMemberId()).size());
        assertEquals(cartRepository.findByMemberId(member.getMemberId()).size(), 0);
    }

    @DisplayName("회원이 주문한 상품을 볼 수 있어야한다")
    @Test
    void getOrder_O() {
        // given
        Member member = getMember();
        Product product = getProduct();
        Orders order = Orders.builder()
                .member(member)
                .product(product)
                .deliveryAddress("경기도 어드곳")
                .cardNumber("1234-1234-1234-1234")
                .build();
        orderRepository.save(order);

        // when
        Long memberId = member.getMemberId();
        orderService.getOrder(memberId);

        // then
        assertEquals(product.getName(), order.getProduct().getName());
        assertEquals(Delivery.SHIPMENT, order.getDelivery());
    }

    @DisplayName("회원이 환불을 하였을때 환불 되었다고 나와야한다.")
    @Test
    void refunded_o() {
        // given
        OrderNumber orderNumber = new OrderNumber();
        Member member = getMember();
        Product product = getProduct();
        Orders order = Orders.builder()
                .orderNumber(orderNumber)
                .member(member)
                .product(product)
                .deliveryAddress("경기도")
                .totalPrice(product.getPrice())
                .build();
        orderRepository.save(order);

        // when
        orderService.refunded(order.getOrderId());

        // then
        assertEquals(true, order.getIsRefunded());
    }

    Member getMember() {
        return memberRepository.findAll().get(0);
    }

    Product getProduct() {
        return productRepository.findAll().get(0);
    }

    Product getProduct2() {
        return  productRepository.findAll().get(1);
    }
}
package com.api.monitormall.service;

import com.api.monitormall.entity.*;
import com.api.monitormall.exception.CountError;
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
        List<Orders> orders = orderRepository.findOrders(member.getMemberId());
        assertEquals(1, orders.size());
        assertEquals(cartRepository.findByMemberId(member.getMemberId()).size(), 0);
        assertEquals(9, product.getCount());
    }

    @DisplayName("제품 수량보다 더 주문할 경우 오류가 발생해야한다.")
    @Test
    void orderAdd_X() {
        // given
        Member member = getMember();

        Product product = getProduct();
        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(100)
                .build();
        cartRepository.save(cart);

        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .cardNumber("1234-1234-1234-1234")
                .build();

        // expected
        assertThrows(CountError.class, () -> {
            orderService.orderAdd(request);
        });
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

    @DisplayName("회원이 주문한 상품을 관리자가 볼 수 있어야한다")
    @Test
    void getOrder_admin_o() {
        // given
        Member member = getMember();
        Product product = getProduct();
        Orders order = Orders.builder()
                .member(member)
                .product(product)
                .productCount(1)
                .deliveryAddress("경기도 어드곳")
                .cardNumber("1234-1234-1234-1234")
                .build();
        orderRepository.save(order);
        Product product2 = getProduct2();
        Orders order2 = Orders.builder()
                .member(member)
                .product(product2)
                .productCount(1)
                .deliveryAddress("경기도 어드곳")
                .cardNumber("1234-1234-1234-1234")
                .build();
        orderRepository.save(order2);

        // when
        List<Orders> findOrders = orderService.getOrders();

        // then
        assertEquals(2, findOrders.size());
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
                .productCount(2)
                .deliveryAddress("경기도")
                .totalPrice(product.getPrice())
                .build();
        orderRepository.save(order);
        product.minus(2);

        // when
        orderService.refunded(order.getOrderId());

        // then
        assertEquals(10, product.getCount());
        assertEquals(true, order.getIsRefunded());
    }



    @DisplayName("회원이 환불을 하였을때 수량도 되돌아가져야함")
    @Test
    void refunded_count_o() {
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
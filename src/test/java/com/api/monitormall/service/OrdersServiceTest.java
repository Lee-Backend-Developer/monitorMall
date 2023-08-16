package com.api.monitormall.service;

import com.api.monitormall.entity.*;
import com.api.monitormall.exception.CountError;
import com.api.monitormall.exception.OrderNotFount;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.*;
import com.api.monitormall.request.OrderAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrdersServiceTest {
    private static final String CARD_NUMBER = "1234-1234-1234-1234";
    private static final String DELIVERY = "경기도 어드곳";
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

        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(1)
                .build();
        cartRepository.save(cart);

        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .cardNumber(CARD_NUMBER)
                .build();

        // when
        orderService.orderAdd(request);

        // then
        List<Orders> orders = orderRepository.findOrders(member.getMemberId());
        assertEquals(1, orders.size());
        assertTrue(cartRepository.findByMemberId(member.getMemberId()).isEmpty());
        Product findProduct = productRepository.findById(product.getProductId()).orElseThrow(ProductNotFount::new);
        assertEquals(9, findProduct.getCount());
    }

    @DisplayName("제품 수량보다 더 주문할 경우 오류가 발생해야한다.")
    @Test
    void orderAdd_X() {
        // given
        Member member = getMember();

        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(100)
                .build();
        cartRepository.save(cart);

        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .cardNumber(CARD_NUMBER)
                .build();

        // expected
        String errorMessage = assertThrows(CountError.class, () -> {
            orderService.orderAdd(request);
        }).getMessage();

        assertEquals("현재 재고 10개 입니다. 이 보다 많이 상품을 담을수 없습니다.", errorMessage);
    }

    @DisplayName("여러개의 주문이 되어야한다.")
    @Test
    void orderAdds_O() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        Product product2 = getProduct("테스트 32인치", 10, 600000, "LG", 32, true, true, true, "/image/LG_product.jpg");

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
                .cardNumber(CARD_NUMBER)
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(2, orderRepository.findOrders(member.getMemberId()).size());
        assertTrue(cartRepository.findByMemberId(member.getMemberId()).isEmpty());
    }

    @DisplayName("회원이 주문한 상품을 볼 수 있어야한다")
    @Test
    void getOrder_O() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        Orders order = getOrder(Orders.builder()
                .member(member)
                .product(product)
                .deliveryAddress(DELIVERY)
                .cardNumber(CARD_NUMBER));

        // when
        Long memberId = member.getMemberId();
        orderService.getOrder(memberId);

        // then
        Orders findOrder = orderRepository.findById(order.getOrderId()).orElseThrow(OrderNotFount::new);
        assertEquals(product.getName(), findOrder.getProduct().getName());
        assertEquals(Delivery.SHIPMENT, findOrder.getDelivery());
    }

    @DisplayName("회원이 주문한 상품을 관리자가 볼 수 있어야한다")
    @Test
    void getOrder_admin_o() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        getOrder(Orders.builder()
                .member(member)
                .product(product)
                .productCount(1)
                .deliveryAddress(DELIVERY)
                .cardNumber(CARD_NUMBER));
        Product product2 = getProduct("테스트 32인치", 10, 600000, "LG", 32, true, true, true, "/image/LG_product.jpg");
        getOrder(Orders.builder()
                .member(member)
                .product(product2)
                .productCount(1)
                .deliveryAddress(DELIVERY)
                .cardNumber(CARD_NUMBER));

        // when
        List<Orders> findOrders = orderService.getOrders();

        // then
        assertEquals(2, findOrders.size());
    }

    @DisplayName("회원이 환불을 하였을때 환불 되어야하고 수량도 되돌아가져야한다.")
    @Test
    void refunded_o() {
        // given
        OrderNumber orderNumber = new OrderNumber();
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 10, "dell", 27, true, true, true, "/image/product.jpg");
        Orders order = getOrder(Orders.builder()
                .orderNumber(orderNumber)
                .member(member)
                .product(product)
                .productCount(2)
                .deliveryAddress("경기도")
                .totalPrice(product.getPrice()));
        product.minus(2);

        // when
        orderService.refunded(order.getOrderId());

        // then
        assertEquals(10, product.getCount());
        assertTrue(order.getIsRefunded());
    }

    private Orders getOrder(Orders.OrdersBuilder orderNumber) {
        Orders order = orderNumber
                .build();
        return orderRepository.save(order);
    }

    private Member getMember() {
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address(DELIVERY)
                .name("홍길동")
                .build();
        return memberRepository.save(member);
    }

    private Product getProduct(String name, int price, int count, String brand, int inch, boolean speaker, boolean usb, boolean dp, String img01) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .count(count)
                .brand(brand)
                .inch(inch)
                .speaker(speaker)
                .usb(usb)
                .dp(dp)
                .img01(img01)
                .build();

        return productRepository.save(product);
    }
}
package com.api.monitormall.service;

import com.api.monitormall.entity.Delivery;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Orders;
import com.api.monitormall.entity.Product;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.OrderNumberRepository;
import com.api.monitormall.repository.OrderRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.OrderAdd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersServiceTest {
    @Autowired
    private MemberRepository memberRepository;
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
                .brand("dell")
                .inch(27)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product.jpg")
                .build();
        productRepository.save(product);
    }

    @DisplayName("하나의 주문이 되어야한다.")
    @Test
    void orderAdd_O() {
        // given
        Member member = getMember();
        Product product = getProduct();
        List<Long> productIds = new ArrayList<>();
        productIds.add(product.getProductId());
        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .productIds(productIds) // todo 개선이 필요한 코드
                .cardNumber("1234-1234-1234-1234")
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(1, orderRepository.count());

        Long orderNumberId = orderNumberRepository.findAll().get(0).getOrderNumberId();
        Orders order = orderRepository.findAll().get(0);
        assertEquals(orderNumberId, order.getOrderNumber().getOrderNumberId());
    }
    @DisplayName("여러개의 주문이 되어야한다.")
    @Test
    void orderAdds_O() {
        // given
        Member member = getMember();
        Product product1 = getProduct();
        Product product2 = Product.builder()
                .name("테스트 32인치")
                .price(600000)
                .brand("LG")
                .inch(32)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/LG_product.jpg")
                .build();
        productRepository.save(product2);
        List<Long> productIds = new ArrayList<>();
        productIds.add(product1.getProductId());
        productIds.add(product2.getProductId());
        OrderAdd request = OrderAdd.builder()
                .memberId(member.getMemberId())
                .productIds(productIds) // todo 개선이 필요한 코드
                .cardNumber("1234-1234-1234-1234")
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(2, orderRepository.count());

        Long orderNumberId = orderNumberRepository.findAll().get(0).getOrderNumberId();
        Orders order = orderRepository.findAll().get(0);
        assertEquals(orderNumberId, order.getOrderNumber().getOrderNumberId());
    }

    @DisplayName("회원이 주문을 볼 수 있어야한다.")
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
        Orders findOrder = orderRepository.findAll().get(0);
        assertEquals(false, findOrder.getIsRefunded());

    }

    @DisplayName("회원이 환불을 하였을때 환불 되었다고 나와야한다.")
    @Test
    void refunded_o() {
        // given


        // when


        // then

    }

    Member getMember() {
        return memberRepository.findAll().get(0);
    }

    Product getProduct() {
        return productRepository.findAll().get(0);
    }
}
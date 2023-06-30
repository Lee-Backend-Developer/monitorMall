package com.api.monitormall.service;

import com.api.monitormall.entity.Delivery;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Order;
import com.api.monitormall.entity.Product;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.OrderRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.OrderAdd;
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
class OrderServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
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

    @DisplayName("주문이 되어야한다.")
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
                .delivery(Delivery.SHIPMENT)
                .cardNumber("1234-1234-1234-1234")
                .build();

        // when
        orderService.orderAdd(request);

        // then
        assertEquals(1, orderRepository.count());
    }

    @DisplayName("회원이 주문을 볼 수 있어야한다.")
    @Test
    void getOrder_O() {
        // given
        Member member = getMember();
        Product product = getProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = Order.builder()
                .member(member)
                .product(products)
                .delivery(Delivery.SHIPMENT)
                .deliveryAddress("경기도 어드곳")
                .cardNumber("1234-1234-1234-1234")
                .isRefunded(false)
                .build();
        orderRepository.save(order);


        // when
        Long memberId = member.getMemberId();
        orderService.getOrder(memberId);

        // then
        Order findOrder = orderRepository.findAll().get(0);
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
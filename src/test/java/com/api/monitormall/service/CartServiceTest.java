package com.api.monitormall.service;

import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.repository.CartRepository;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.CartAdd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

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

    @AfterEach
    void delete() {
        cartRepository.deleteAll();
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("카트에 물건이 추가가 되어야한다")
    @Test
    void addCart_O() {
        // given
        Long memberId = getMember().getMemberId();
        Long productId = getProduct().getProductId();

        CartAdd cart = CartAdd.builder()
                .memberId(memberId)
                .productId(productId)
                .build();

        // when
        cartService.addCart(cart);

        // then
        assertEquals(1, cartRepository.count());
    }

    @DisplayName("카트 가져오기")
    @Test
    void getCart_O() {
        // given
        Member member = getMember();
        Product product = getProduct();

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .build();
        cartRepository.save(cart);

        // when
        Long memberId = member.getMemberId();
        List<Cart> carts = cartService.getCart(memberId);

        // then
        assertEquals(1, carts.size());
        assertEquals(1, cartRepository.count());
    }

    @DisplayName("카트에 담긴 상품이 삭제가 되어야한다.")
    @Test
    void cartDelete_O() {
        // given
        Member member = getMember();
        Product product = getProduct();

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .build();
        cartRepository.save(cart);

        // when
        Long cartId = cart.getCartId();
        cartService.deleteCart(cartId);

        // then
        assertEquals(0, cartRepository.count());
    }


    Member getMember() {
        return memberRepository.findAll().get(0);
    }

    Product getProduct() {
        return productRepository.findAll().get(0);
    }
}
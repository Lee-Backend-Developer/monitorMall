package com.api.monitormall.service;

import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.ProductCountError;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.CartRepository;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.CartAdd;
import com.api.monitormall.request.CartProduct;
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
                .count(1)
                .brand("dell")
                .inch(27)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product.jpg")
                .build();
        productRepository.save(product);

        Product product02 = Product.builder()
                .name("테스트 32인치")
                .price(500000)
                .count(1)
                .brand("dell")
                .inch(32)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product02.jpg")
                .build();
        productRepository.save(product02);
    }

    @AfterEach
    void delete() {
        cartRepository.deleteAll();
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("카트에 여러 물건이 추가가 되어야한다")
    @Test
    void addCart_O() {
        // given
        Long memberId = getMember().getMemberId();
        List<Long> productIds = new ArrayList<>();

        getProducts()
                .forEach(product -> productIds.add(product.getProductId()));

        CartProduct cartProduct1 = CartAdd.cartProductCreate(productIds.get(0), 1);
        CartProduct cartProduct2 = CartAdd.cartProductCreate(productIds.get(1), 1);

        CartAdd cart = new CartAdd(memberId, cartProduct1, cartProduct2);

        // when
        cartService.addCart(cart);

        // then
        assertEquals(2, cartRepository.findCart(memberId).size());
    }

    @DisplayName("물건 수량이 부족할 경우 카트에 담을때 오류가 발생해야됨")
    @Test
    void addCart_X() {
        // given
        Long memberId = getMember().getMemberId();
        List<Long> productIds = new ArrayList<>();

        getProducts()
                .forEach(product -> productIds.add(product.getProductId()));

        CartProduct cartProduct1 = CartAdd.cartProductCreate(productIds.get(0), 2);
        CartProduct cartProduct2 = CartAdd.cartProductCreate(productIds.get(1), 3);

        CartAdd cart = new CartAdd(memberId, cartProduct1, cartProduct2);

        // expected
        assertThrows(ProductCountError.class, () -> {
            cartService.addCart(cart);
        });
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
        assertEquals(2, carts.size());
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

    List<Product> getProducts() {
        return productRepository.findAll();
    }
}
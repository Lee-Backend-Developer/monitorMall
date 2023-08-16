package com.api.monitormall.service;

import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.CountError;
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
        getMember();
        getProduct("테스트 27인치", 300000, 5, "dell", 27, true, true, true, "/image/product.jpg");
        getProduct("테스트 32인치", 500000, 10, "dell", 27, true, true, true, "/image/product02.jpg");
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
        assertEquals(2, cartRepository.findCarts(memberId).size());
    }

    @DisplayName("물건 수량이 부족할 경우 카트에 담을때 오류가 발생해야됨")
    @Test
    void addCart_X() {
        // given
        Long memberId = getMember().getMemberId();
        List<Long> productIds = new ArrayList<>();

        getProducts()
                .forEach(product -> productIds.add(product.getProductId()));

        CartProduct cartProduct1 = CartAdd.cartProductCreate(productIds.get(0), 10);
        CartProduct cartProduct2 = CartAdd.cartProductCreate(productIds.get(1), 4);

        CartAdd cart = new CartAdd(memberId, cartProduct1, cartProduct2);

        // expected
        String message = assertThrows(CountError.class, () -> {
            cartService.addCart(cart);
        }).getMessage();

        assertEquals("현재 재고 5개 입니다. 이 보다 많이 상품을 담을수 없습니다.", message);
    }

    @DisplayName("카트 가져오기")
    @Test
    void getCart_O() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 5, "dell", 27, true, true, true, "/image/product.jpg");

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(1)
                .build();
        cartRepository.save(cart);

        // when
        Long memberId = member.getMemberId();
        List<Cart> carts = cartService.getCart(memberId);

        // then
        assertEquals(1, carts.size());
        assertEquals(1, cartRepository.count());
    }

    @DisplayName("카트에 담긴 상품이 갯수가 수정이 되어야한다.")
    @Test
    void cartCntEdit_O() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 5, "dell", 27, true, true, true, "/image/product.jpg");

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .count(1)
                .build();
        cartRepository.save(cart);

        // when
        cartService.cartCntEdit(cart.getCartId(), cart.getProduct().getProductId(), 4);

        // then
        assertEquals(4, cart.getCount());
    }

    @DisplayName("카트에 담긴 상품이 삭제가 되어야한다.")
    @Test
    void cartDelete_O() {
        // given
        Member member = getMember();
        Product product = getProduct("테스트 27인치", 300000, 5, "dell", 27, true, true, true, "/image/product.jpg");

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
        Member member = Member.builder()
                .loginId("hong1")
                .password("1234")
                .address("경기도 어느곳")
                .name("홍길동")
                .build();
        return memberRepository.save(member);
    }

    Product getProduct(String name, int price, int count, String brand, int inch, boolean speaker, boolean usb, boolean dp, String img01) {
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

    List<Product> getProducts() {
        return productRepository.findAll();
    }
}
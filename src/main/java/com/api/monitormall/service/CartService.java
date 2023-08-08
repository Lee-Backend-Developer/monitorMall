package com.api.monitormall.service;


import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.*;
import com.api.monitormall.repository.CartRepository;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.CartAdd;
import com.api.monitormall.request.CartProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addCart(CartAdd request) {
        Member member = memberRepository
                .findById(request.getMemberId())
                .orElseThrow(MemberNotFount::new);

        List<CartProduct> requestProducts = request.getProducts();
        List<Product> products = requestProducts // 요청으로 제품이 올 때 제품이 존재하는지 검증
                .stream()
                .map(cartProduct -> productRepository.findById(cartProduct.getProductId())
                        .orElseThrow(ProductNotFount::new))
                .collect(Collectors.toList());

//        productValidation(product, request.getCount());
//
//        product.minus(request.getCount()); // 제품 재고가 빠지는지 아닌지 확인

        products.forEach(product -> {
            Cart cart = Cart.builder()
                    .member(member)
                    .product(product)
                    .build();
            cartRepository.save(cart);
        });
    }

    private static void productValidation(Product product, int count) {
        if(product.getCount() <= 0) { // 상품이 없을경우 카트에 담길수 없음
            throw new ProductAddError();
        }

        // 상품에 수량보다 더 카트에 담을경우 에러
        long countChk = product.getCount() - count;
        log.info("countChk => {}", countChk);
        log.info("product count => {}", product.getCount());

        if(countChk < 0 ) {
            throw new ProductCountError(product.getCount());
        }
    }

    public List<Cart> getCart(Long memberId) {
        return cartRepository.findCart(memberId);
    }

    public void cartCntEdit(Long cartId, Long productId, Long cnt) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFount::new);
        cart.getProduct();

    }

    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}

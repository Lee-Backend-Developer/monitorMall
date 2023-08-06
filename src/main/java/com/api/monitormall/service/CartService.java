package com.api.monitormall.service;


import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.MemberNotFount;
import com.api.monitormall.exception.ProductAddError;
import com.api.monitormall.exception.ProductCountError;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.CartRepository;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.CartAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFount::new);

        if(product.getCount() <= 0) { // 상품이 없을경우 카트에 담길수 없음
            throw new ProductAddError();
        }

        // 상품에 수량보다 더 카트에 담을경우 에러
        long countChk = product.getCount() - request.getCount();
        log.info("countChk => {}", countChk);
        log.info("product count => {}", product.getCount());

        if(countChk < 0 ) {
            throw new ProductCountError(product.getCount());
        }

        product.minus(request.getCount());

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .build();
        cartRepository.save(cart);
    }

    public List<Cart> getCart(Long memberId) {
        return cartRepository.findCart(memberId);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}

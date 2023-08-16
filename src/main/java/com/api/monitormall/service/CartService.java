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

        requestProducts.forEach(requestProduct -> {
            Product product = productRepository.findById(requestProduct.getProductId()) // 요청으로 제품이 올 때 제품이 존재하는지 검증
                    .orElseThrow(ProductNotFount::new);

            cartValidation(product, requestProduct.getCount());

            Cart cart = Cart.builder()
                    .member(member)
                    .product(product)
                    .count(requestProduct.getCount())
                    .build();
            cartRepository.save(cart);
        });
    }

    public List<Cart> getCart(Long memberId) {
        return cartRepository.findCarts(memberId);
    }

    @Transactional
    public void cartCntEdit(Long cartId, Long productId, int cnt) {
        Cart cart = cartRepository.findCart(cartId, productId)
                .orElseThrow(CartNotFount::new);
        cart.setCount(cnt);
        //todo 카트 제품 수량 수정할 때 검증 구현해야됨
    }

    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    private static void cartValidation(Product product, int count) {
        if(product.getCount() <= 0) { // 상품이 없을경우 카트에 담길수 없음
            throw new ProductAddError();
        }

        // 상품에 수량보다 더 카트에 담을경우 에러
        long countChk = product.getCount() - count;

        if(countChk < 0 ) {
            throw new CountError(product.getCount());
        }
    }
}

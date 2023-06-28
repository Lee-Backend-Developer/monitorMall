package com.api.monitormall.service;


import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.Member;
import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.MemberNotFount;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.CartRepository;
import com.api.monitormall.repository.MemberRepository;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.CartAdd;
import lombok.RequiredArgsConstructor;
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

    // todo 카트에 물건 담기, 회원 아이디를 통해서 카트를 가져오기, 카트 번호를 통해서 삭제

    @Transactional
    public void addCart(CartAdd request) {
        Member member = memberRepository
                .findById(request.getMemberId())
                .orElseThrow(MemberNotFount::new);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFount::new);

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .build();
        cartRepository.save(cart);
    }

    public List<Cart> getCart(Long memberId) {
        return cartRepository.findCart(memberId);
    }
}

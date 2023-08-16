package com.api.monitormall.repository;

import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.api.monitormall.entity.QCart.*;


@Repository
@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cart> findCarts(Long memberId) {
        return queryFactory.selectFrom(cart)
                .where(cart.member.memberId.eq(memberId)).fetch();
    }

    // todo Optional 검토 해봐야됨
    @Override
    public List<Cart> findByMemberId(Long memberId) {
        return queryFactory.selectFrom(cart)
                .where(cart.member.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public Optional<Cart> findCart(Long cartId, Long productId) {
        return Optional.of(queryFactory.selectFrom(cart)
                .where(cart.cartId.eq(cartId).and(cart.product.productId.eq(productId)))
                .fetchOne());
    }
}

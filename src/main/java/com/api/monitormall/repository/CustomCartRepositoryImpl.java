package com.api.monitormall.repository;

import com.api.monitormall.entity.Cart;
import com.api.monitormall.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.monitormall.entity.QCart.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cart> findCart(Long memberId) {
        return queryFactory.selectFrom(cart)
                .where(cart.member.memberId.eq(memberId)).fetch();
    }
}

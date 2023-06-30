package com.api.monitormall.repository;

import com.api.monitormall.entity.Order;
import com.api.monitormall.entity.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.monitormall.entity.QOrder.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findOrders(Long memberId) {
        return queryFactory.selectFrom(order)
                .where(order.member.memberId.eq(memberId).and(order.isRefunded.eq(false)))
                .fetch();
    }
}

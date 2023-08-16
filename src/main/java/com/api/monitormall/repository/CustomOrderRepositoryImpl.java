package com.api.monitormall.repository;

import com.api.monitormall.entity.Orders;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.monitormall.entity.QOrders.*;


@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Orders> findOrders(Long memberId) {
        return queryFactory.selectFrom(orders)
                .where(orders.member.memberId.eq(memberId).and(orders.isRefunded.eq(false)))
                .fetch();
    }
}

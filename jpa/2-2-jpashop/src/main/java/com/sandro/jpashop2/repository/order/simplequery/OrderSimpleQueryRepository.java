package com.sandro.jpashop2.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderSimpleDtos() {
        String jpql = "select new com.sandro.jpashop2.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d";
        return em.createQuery(jpql, OrderSimpleQueryDto.class)
                .getResultList();
    }
}

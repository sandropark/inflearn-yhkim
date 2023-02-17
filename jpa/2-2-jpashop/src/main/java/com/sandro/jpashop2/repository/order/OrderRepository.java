package com.sandro.jpashop2.repository.order;

import com.sandro.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    /**
     * 동적 쿼리
     */

/**
    // == 1. 문자열로 만들기 == //
    public List<Order> findAll(OrderSearch orderSearch) {
        String jpql = getJpql(orderSearch);
        TypedQuery<Order> query = setParamAndGetQuery(orderSearch, jpql);
        return query.getResultList();
    }
    private String getJpql(OrderSearch orderSearch) {
        StringBuilder sb = new StringBuilder();
        sb.append("select o from Order o join o.member m ");
        if (orderSearch.getOrderStatus() != null) {
            sb.append("where o.status = :status ");
        }
        if (orderSearch.getMemberName() != null) {
            andOrWhere(orderSearch, sb);
            sb.append("m.name like :name");
        }
        return sb.toString();
    }

    private void andOrWhere(OrderSearch orderSearch, StringBuilder sb) {
        if (orderSearch.getOrderStatus() != null) {
            sb.append("and ");
        } else {
            sb.append("where ");
        }
    }

    private TypedQuery<Order> setParamAndGetQuery(OrderSearch orderSearch, String jpql) {
        TypedQuery<Order> query = em.createQuery(jpql, Order.class);
        if (orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (orderSearch.getMemberName() != null) {
            query.setParameter("name", orderSearch.getMemberName());
        }
        return query;
    }
    // == ================================================================================ == //
*/

    // == 2. Criteria 로 만들기 == //
    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> o = query.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if (orderSearch.getMemberName() != null) {
            Predicate name = cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        return em.createQuery(query).getResultList();
    }

    // == ================================================================================ == //
    public List<Order> findAllWithMemberDelivery() {
        String jpql = "select o " +
                "from Order o " +
                "join fetch o.member " +
                "join fetch o.delivery";
        return em.createQuery(jpql, Order.class)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        String jpql = "select distinct o from Order o " +
                "join fetch o.member " +
                "join fetch o.delivery " +
                "join fetch o.orderItems oi " +
                "join fetch oi.item";
        return em.createQuery(jpql, Order.class)
                .getResultList();
    }
}
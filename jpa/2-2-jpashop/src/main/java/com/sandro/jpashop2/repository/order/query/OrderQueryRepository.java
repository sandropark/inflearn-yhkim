package com.sandro.jpashop2.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderDtos() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimized() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(orderQueryDto -> {
            orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId()));
        });

        return result;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        String jpql = "select new com.sandro.jpashop2.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d " +
                "join o.orderItems oi " +
                "join oi.item i";
        return em.createQuery(jpql, OrderFlatDto.class).getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        String jpql = "select new com.sandro.jpashop2.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d";
        return em.createQuery(jpql, OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        String jpql = "select new com.sandro.jpashop2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                "from OrderItem oi " +
                "join oi.item i " +
                "where oi.order.id = :orderId";
        return em.createQuery(jpql, OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        String jpql = "select new com.sandro.jpashop2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                "from OrderItem oi " +
                "join oi.item i " +
                "where oi.order.id in :orderIds";
        List<OrderItemQueryDto> orderItems = em.createQuery(jpql, OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        return orderItems.stream()
                .collect(groupingBy(OrderItemQueryDto::getOrderId));
    }
}

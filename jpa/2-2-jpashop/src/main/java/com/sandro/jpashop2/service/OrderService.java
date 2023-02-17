package com.sandro.jpashop2.service;

import com.sandro.jpashop2.domain.*;
import com.sandro.jpashop2.domain.item.Item;
import com.sandro.jpashop2.repository.ItemRepository;
import com.sandro.jpashop2.repository.MemberRepository;
import com.sandro.jpashop2.repository.order.OrderRepository;
import com.sandro.jpashop2.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문하기
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    // 취소
    /* 트랜잭션 안에서 영속성 컨텍스트로 관리되는 엔티티는 변경사항이 있을 경우
    commit 시점에 자동으로 update 쿼리가 날라간다. */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색
    public List<Order> searchOrder(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}

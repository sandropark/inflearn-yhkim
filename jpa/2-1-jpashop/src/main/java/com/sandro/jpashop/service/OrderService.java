package com.sandro.jpashop.service;

import com.sandro.jpashop.domain.*;
import com.sandro.jpashop.domain.item.Item;
import com.sandro.jpashop.repository.ItemRepository;
import com.sandro.jpashop.repository.MemberRepository;
import com.sandro.jpashop.repository.OrderRepository;
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

    /**
     * 주문
     * 1. 회원 id
     * 2. 상품 id
     * 3. 수량
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delevery delevery = new Delevery();
        delevery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delevery, orderItem);

        /**
         * 주문저장
         * delivery와 orderItem은 따로 repository를 생성하지 않고 order객체에서 cascade 옵션으로 한 번에 처리했다.
         * 이렇게 한 이유는 두 객체는 order에서만 사용되기 때문이다. order의 라이프사이클에 종속적이기 때문이다.
         */
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문엔티티 조회
        Order targetOrder = orderRepository.findOne(orderId);
        // 주문 취
        targetOrder.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
//        return orderRepository.findAllByCriteria(orderSearch);
    }
}

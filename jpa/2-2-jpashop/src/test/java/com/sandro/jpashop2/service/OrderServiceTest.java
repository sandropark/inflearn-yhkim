package com.sandro.jpashop2.service;

import com.sandro.jpashop2.domain.*;
import com.sandro.jpashop2.domain.item.Book;
import com.sandro.jpashop2.repository.order.OrderRepository;
import com.sandro.jpashop2.repository.order.OrderSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired OrderRepository orderRepository;
    String member1Name;
    Member member1;
    Member member2;
    Book book;
    int stockQuantity;
    int price;

    @BeforeEach
    void setUp() {
        Address address1 = new Address("seoul", "123st", "12345");
        Address address2 = new Address("seoul", "123st", "12345");
        member1Name = "sandro";
        member1 = new Member(member1Name, address1);
        member2 = new Member("gogo", address2);
        stockQuantity = 10;
        price = 20000;
        book = new Book("사피엔스", price, stockQuantity, "하라리", "12345678");

        memberService.join(member1);
        memberService.join(member2);
        itemService.saveItem(book);
    }

    @DisplayName("주문 생성 : 주문한 수량만큼 재고 수량이 줄어야 한다.")
    @Test
    void createOrder() throws Exception {
        // given
        int orderQuantity = 5;
        Long orderedId = orderService.order(member1.getId(), book.getId(), orderQuantity);

        // when
        Order foundOrder = orderRepository.findOne(orderedId);

        // then
        assertThat(foundOrder.getOrderItems().get(0).getItem().getStockQuantity())
                .isEqualTo(stockQuantity - orderQuantity);
        assertThat(foundOrder.getTotalPrice()).isEqualTo(price * orderQuantity);
        assertThat(foundOrder.getStatus()).isSameAs(OrderStatus.ORDER);
        assertThat(foundOrder.getDelivery().getStatus()).isSameAs(DeliveryStatus.READY);
        assertThat(foundOrder.getMember()).isSameAs(member1);
        assertThat(foundOrder.getOrderItems().get(0).getItem()).isSameAs(book);
    }

    @DisplayName("주문 취소 : 주문 상태가 바뀌고, 재고수량이 원복돼야 한다.")
    @Test
    void cancel() throws Exception {
        // given
        Long orderedId = orderService.order(member1.getId(), book.getId(), 5);

        // when
        orderService.cancelOrder(orderedId);
        Order canceledOrder = orderRepository.findOne(orderedId);

        // then
        assertThat(canceledOrder.getStatus()).isSameAs(OrderStatus.CANCEL); // 주문 상태 CANCEL 로 변경
        assertThat(canceledOrder.getOrderItems().get(0).getItem().getStockQuantity())
                .isEqualTo(stockQuantity);  // 상품 재고 원복
    }

    @DisplayName("검색 : 아무 조건이 없을 때")
    @Test
    void searchByNothing() throws Exception {
        // given
        makeThreeOrders();

        OrderSearch orderSearch = new OrderSearch();

        // when
        List<Order> foundOrders = orderService.searchOrder(orderSearch);

        // then
        assertThat(foundOrders).hasSize(3);
    }

    @DisplayName("검색 : 이름으로 검색")
    @Test
    void searchByName() throws Exception {
        // given
        makeThreeOrders();

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member1Name);

        // when
        List<Order> foundOrders = orderService.searchOrder(orderSearch);

        // then
        assertThat(foundOrders).hasSize(2);

    }

    @DisplayName("검색 : 주문 상태로 검색")
    @Test
    void searchByStatus() throws Exception {
        // given
        makeThreeOrders();

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        // when
        List<Order> foundOrders = orderService.searchOrder(orderSearch);

        // then
        assertThat(foundOrders).hasSize(2);
    }

    @DisplayName("검색 : 이름과 상태로 검색")
    @Test
    void searchByAll() throws Exception {
        // given
        makeThreeOrders();

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member1Name);
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        // when
        List<Order> foundOrders = orderService.searchOrder(orderSearch);

        // then
        assertThat(foundOrders).hasSize(2);
    }

    private void makeThreeOrders() {
        orderService.order(member1.getId(), book.getId(), 1);
        orderService.order(member1.getId(), book.getId(), 2);
        Long order3Id = orderService.order(member2.getId(), book.getId(), 3);
        orderService.cancelOrder(order3Id);
    }

































}
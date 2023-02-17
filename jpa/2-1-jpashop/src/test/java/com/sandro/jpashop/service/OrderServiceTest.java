package com.sandro.jpashop.service;

import com.sandro.jpashop.domain.Address;
import com.sandro.jpashop.domain.Member;
import com.sandro.jpashop.domain.Order;
import com.sandro.jpashop.domain.OrderStatus;
import com.sandro.jpashop.domain.item.Book;
import com.sandro.jpashop.domain.item.Item;
import com.sandro.jpashop.exception.NotEnoughStockException;
import com.sandro.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    Member member;

    @BeforeEach
    public void setUp() throws Exception {
        member = createMember();
    }

    @Test
    public void 주문() throws Exception {
        // given
        int price = 10000;
        int quantity = 10;
        Book book = createBook("사피엔스", price, quantity);

        // when
        int orderCount = 10;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order foundOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, foundOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(member, foundOrder.getMember(), "주문한 고객이 같아야 한다.");
        assertEquals(1, foundOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(price * orderCount, foundOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(0, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 주문시_재고수량초과() throws Exception {
        // given
        int quantity = 10;
        Item item = createBook("사피엔스", 10000, quantity);

        // when
        int orderCount = 11;

        assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    public void 주문_취소() throws Exception {
        // given
        int quantity = 10;
        Item item = createBook("사피엔스", 10000, quantity);
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        // when
        orderService.cancelOrder(orderId);  // 주문 취소

        // then
        Order canceledOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, canceledOrder.getStatus(), "상태는 CANCEL이어야 한다.");
        assertEquals(quantity, item.getStockQuantity(), "재고의 수량은 원래대로 돌아와야 한다.");
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("sandro");
        member.setAddress(new Address("서울", "종로", "12345"));
        em.persist(member);
        return member;
    }

    private Book createBook(String title, int price, int quantity) {
        Book book = new Book();
        book.setName(title);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }
}
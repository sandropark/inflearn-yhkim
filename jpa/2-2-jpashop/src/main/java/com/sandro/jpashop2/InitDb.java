package com.sandro.jpashop2;

import com.sandro.jpashop2.domain.*;
import com.sandro.jpashop2.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = new Member("sandro", new Address("seoul", "1", "11111"));
            em.persist(member1);

            Book book1 = new Book("사피엔스", 10000, 10, "하라리", "12345678");
            Book book2 = new Book("사피엔스2", 20000, 10, "하라리", "12345678");
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery(member1.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
        public void dbInit2() {
            Member member2 = new Member("gogo", new Address("busan", "2", "22222"));
            em.persist(member2);

            Book book1 = new Book("총균쇠", 30000, 10, "다이아몬드", "12345678");
            Book book2 = new Book("총균쇠2", 40000, 10, "다이아몬드", "12345678");
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery(member2.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}

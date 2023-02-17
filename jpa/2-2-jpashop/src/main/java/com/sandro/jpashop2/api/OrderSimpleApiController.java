package com.sandro.jpashop2.api;

import com.sandro.jpashop2.domain.Address;
import com.sandro.jpashop2.domain.Order;
import com.sandro.jpashop2.domain.OrderStatus;
import com.sandro.jpashop2.repository.order.OrderRepository;
import com.sandro.jpashop2.repository.order.OrderSearch;
import com.sandro.jpashop2.repository.order.simplequery.OrderSimpleQueryDto;
import com.sandro.jpashop2.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/v2/simple-orders")
    public List<OrderSimpleDto> ordersV2() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        return all.stream()
                .map(OrderSimpleDto::new)
                .collect(toList());
    }

    @GetMapping("/v3/simple-orders")
    public List<OrderSimpleDto> ordersV3() {
        List<Order> all = orderRepository.findAllWithMemberDelivery();
        return all.stream()
                .map(OrderSimpleDto::new)
                .collect(toList());
    }

    @GetMapping("/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderSimpleDtos();
    }

    @Data
    static class OrderSimpleDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}


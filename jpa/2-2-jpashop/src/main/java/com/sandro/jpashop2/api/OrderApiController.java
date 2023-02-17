package com.sandro.jpashop2.api;

import com.sandro.jpashop2.domain.Address;
import com.sandro.jpashop2.domain.Order;
import com.sandro.jpashop2.domain.OrderItem;
import com.sandro.jpashop2.domain.OrderStatus;
import com.sandro.jpashop2.repository.order.OrderRepository;
import com.sandro.jpashop2.repository.order.OrderSearch;
import com.sandro.jpashop2.repository.order.query.OrderFlatDto;
import com.sandro.jpashop2.repository.order.query.OrderItemQueryDto;
import com.sandro.jpashop2.repository.order.query.OrderQueryDto;
import com.sandro.jpashop2.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/v1/orders")
    public List<Order> ordersV1() {
        return orderRepository.findAll(new OrderSearch());
    }

    @GetMapping("/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/v3.1/orders")
    public List<OrderDto> ordersV3_page() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderDtos();
    }

    @GetMapping("/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimized();
    }

    @GetMapping("/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(),
                        e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @Data
    static class OrderDto {
        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.id = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }

        @Data
        static class OrderItemDto {
            private String itemName;
            private int orderPrice;
            private int count;

            public OrderItemDto(OrderItem orderItem) {
                itemName = orderItem.getItem().getName();
                orderPrice = orderItem.getOrderPrice();
                count = orderItem.getCount();
            }
        }
    }
}

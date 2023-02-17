package com.sandro.jpashop2.controller;

import com.sandro.jpashop2.domain.Member;
import com.sandro.jpashop2.domain.Order;
import com.sandro.jpashop2.domain.item.Item;
import com.sandro.jpashop2.repository.order.OrderSearch;
import com.sandro.jpashop2.service.ItemService;
import com.sandro.jpashop2.service.MemberService;
import com.sandro.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {


    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(@RequestParam Long memberId,
                              @RequestParam Long itemId,
                              @RequestParam int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.searchOrder(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}

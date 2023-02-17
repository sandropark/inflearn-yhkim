package com.sandro.jpashop.controller;

import com.sandro.jpashop.domain.Member;
import com.sandro.jpashop.domain.Order;
import com.sandro.jpashop.domain.OrderSearch;
import com.sandro.jpashop.domain.item.Item;
import com.sandro.jpashop.service.ItemService;
import com.sandro.jpashop.service.MemberService;
import com.sandro.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String list(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        /** orderService의 findOrders를 보면 다른 로직없이 단순히 repository 메서드의 중간다리 역할이다.
         * 이럴경우 controller에서 바로 repository를 호출하는 것도 코드를 단순화하기에는 괜찮다.
         */
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}

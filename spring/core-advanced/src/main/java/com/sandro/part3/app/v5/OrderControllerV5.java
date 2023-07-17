package com.sandro.part3.app.v5;

import com.sandro.part3.trace.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return template.execute(
                "OrderController.request()",
                () -> {
                    orderService.orderItem(itemId);
                    return "ok";
                }
        );
    }

}

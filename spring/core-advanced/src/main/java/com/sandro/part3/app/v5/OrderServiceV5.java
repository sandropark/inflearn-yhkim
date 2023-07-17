package com.sandro.part3.app.v5;

import com.sandro.part3.trace.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public void orderItem(String itemId) {
        template.execute(
                "OrderService.orderItem()",
                () -> {
                    orderRepository.save(itemId);
                    return null;
                }
        );
    }

}

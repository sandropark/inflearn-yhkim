package com.sandro.part3.app.v5;

import com.sandro.part3.trace.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public void save(String itemId) {
        template.execute("OrderRepository.save()",
                () -> {
                    if (itemId.equals("ex"))
                        throw new IllegalStateException("예외 발생!");
                    sleep(1000);
                    return null;
                });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

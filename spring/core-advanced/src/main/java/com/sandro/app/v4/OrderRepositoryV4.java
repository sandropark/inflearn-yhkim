package com.sandro.app.v4;

import com.sandro.trace.logtrace.LogTrace;
import com.sandro.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV4 {
    private final LogTrace trace;

    public void save(String itemId) {
        new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                if (itemId.equals("ex"))
                    throw new IllegalStateException("예외 발생!");
                sleep(1000);
                return null;
            }
        }.execute("OrderRepository.save()");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

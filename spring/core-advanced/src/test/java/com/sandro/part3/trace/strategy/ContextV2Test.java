package com.sandro.part3.trace.strategy;

import com.sandro.part3.trace.strategy.code.ContextV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV2Test {

    @Test
    void strategy() throws Exception {
        ContextV2 context = new ContextV2();

        context.execute(() -> log.info("비즈니스 로직 1 실행"));
        context.execute(() -> log.info("비즈니스 로직 2 실행"));
    }
}

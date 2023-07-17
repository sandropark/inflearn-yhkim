package com.sandro.part3.trace.strategy;

import com.sandro.part3.trace.strategy.code.ContextV1;
import com.sandro.part3.trace.strategy.code.StrategyLogic1;
import com.sandro.part3.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV1Test {

    @Test
    void strategy() throws Exception {
        new ContextV1(new StrategyLogic1()).execute();
        new ContextV1(new StrategyLogic2()).execute();
    }

    @Test
    void strategy2() throws Exception {
        new ContextV1(() -> log.info("비즈니스 로직 1 실행")).execute();
        new ContextV1(() -> log.info("비즈니스 로직 2 실행")).execute();
    }
}

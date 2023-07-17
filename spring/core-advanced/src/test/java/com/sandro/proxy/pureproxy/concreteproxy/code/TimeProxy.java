package com.sandro.proxy.pureproxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();
        String result = super.operation();
        long endTime = System.currentTimeMillis();

        log.info("TimeDecorator 종료 resultTime = {}ms", endTime - startTime);
        return result;
    }
}

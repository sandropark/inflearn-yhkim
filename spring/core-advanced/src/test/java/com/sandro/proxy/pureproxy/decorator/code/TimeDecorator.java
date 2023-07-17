package com.sandro.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();
        String data = component.operation();
        long endTime = System.currentTimeMillis();

        log.info("TimeDecorator 종료 resultTime = {}ms", endTime - startTime);
        return data;
    }
}

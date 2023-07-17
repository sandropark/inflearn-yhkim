package com.sandro.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component {

    private final Component component;
    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String data = component.operation();
        String decoResult = "*****" + data + "*****";
        log.info("MessageDecorator 꾸미기 적용 전 = {}, 적용 후 = {}", data, decoResult);
        return decoResult;
    }
}

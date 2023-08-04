package com.sandro.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class TxBasicTest extends TestSupport {

    @Autowired
    BasicService basicService;

    @Test
    void tx() throws Exception {
        log.info("basicService.class={}", basicService.getClass()); // CGLIB를 사용해서 상속받아 프록시를 생성
        assertThat(AopUtils.isAopProxy(basicService)).isTrue();
        assertThat(basicService.tx()).isTrue();
        assertThat(basicService.nonTx()).isFalse();
    }

}

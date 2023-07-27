package com.sandro.order.aop.exam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ExamTest extends ExamTestSupport {

    @Test
    void test() throws Exception {
        for (int i = 1; i < 6; i++) {
//            log.info("client request={}", i);
            examService.request("data" + i);
        }
    }
}

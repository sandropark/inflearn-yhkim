package com.sandro.part3.trace.template;

import com.sandro.part3.trace.template.code.AbstractTemplate;
import com.sandro.part3.trace.template.code.SubClassLogic1;
import com.sandro.part3.trace.template.code.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class TemplateMethodTest {
    
    @Test
    void templateMethod1() throws Exception {
        AbstractTemplate template = new SubClassLogic1();
        template.execute();
    }

    @Test
    void templateMethod2() throws Exception {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };

        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };

        template2.execute();
    }

    @Test
    void test() throws Exception {
        Template template = () -> log.info("비즈니스 로직 실행");
        template.execute();
    }
    
}

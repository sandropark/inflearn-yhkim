package com.sandro.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
class ReflectionTest {

    @Test
    void noReflection() throws Exception {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        // 공통 로직2 종료
    }

    @Test
    void reflection() throws Exception {
        // 클래스 정보
        Class<?> classHello = Class.forName("com.sandro.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA 메서드 정보
        Method callA = classHello.getMethod("callA");
        Object result1 = callA.invoke(target);
        log.info("result1={}", result1);

        // callB 메서드 정보
        Method callB = classHello.getMethod("callB");
        Object result2 = callB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("com.sandro.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        dynamicCall(classHello.getMethod("callA"), target);
        dynamicCall(classHello.getMethod("callB"), target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

}

package com.sandro.proxy.proxyfactory;

import com.sandro.proxy.common.advice.TimeAdvice;
import com.sandro.proxy.common.service.ConcreteService;
import com.sandro.proxy.common.service.ServiceImpl;
import com.sandro.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProxyFactoryTest {

    @Test
    void interfaceProxy() throws Exception {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) factory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    void concreteProxy() throws Exception {
        ConcreteService target = new ConcreteService();
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) factory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    void proxyTargetClass() throws Exception {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvice(new TimeAdvice());
        factory.setProxyTargetClass(true);  // 인터페이스가 있어도  CGLIB로 생성한다.

        ServiceInterface proxy = (ServiceInterface) factory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}

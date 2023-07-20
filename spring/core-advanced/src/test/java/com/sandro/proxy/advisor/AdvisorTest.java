package com.sandro.proxy.advisor;

import com.sandro.proxy.common.advice.TimeAdvice;
import com.sandro.proxy.common.service.ServiceImpl;
import com.sandro.proxy.common.service.ServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

class AdvisorTest {

    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory;

    @Test
    void advisor3() throws Exception {
        proxyFactory = new ProxyFactory(target);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}

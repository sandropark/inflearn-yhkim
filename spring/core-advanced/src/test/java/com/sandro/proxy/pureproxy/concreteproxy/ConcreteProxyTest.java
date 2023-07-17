package com.sandro.proxy.pureproxy.concreteproxy;

import com.sandro.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import com.sandro.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import com.sandro.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

class ConcreteProxyTest {

    @Test
    void noProxy() throws Exception {
        ConcreteClient client = new ConcreteClient(new ConcreteLogic());

        client.execute();
    }

    @Test
    void addProxy() throws Exception {
        ConcreteClient client = new ConcreteClient(new TimeProxy());

        client.execute();
    }

}

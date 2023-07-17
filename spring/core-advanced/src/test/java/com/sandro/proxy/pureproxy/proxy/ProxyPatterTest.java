package com.sandro.proxy.pureproxy.proxy;

import com.sandro.proxy.pureproxy.proxy.code.CacheProxy;
import com.sandro.proxy.pureproxy.proxy.code.ProxyPatternClient;
import com.sandro.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

class ProxyPatterTest {

    @Test
    void noProxy() throws Exception {
        ProxyPatternClient client = new ProxyPatternClient(new RealSubject());

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxy() throws Exception {
        ProxyPatternClient client = new ProxyPatternClient(new CacheProxy(new RealSubject()));

        client.execute();
        client.execute();
        client.execute();
    }
}

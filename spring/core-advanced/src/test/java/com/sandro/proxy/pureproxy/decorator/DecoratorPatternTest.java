package com.sandro.proxy.pureproxy.decorator;

import com.sandro.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import com.sandro.proxy.pureproxy.decorator.code.MessageDecorator;
import com.sandro.proxy.pureproxy.decorator.code.RealComponent;
import com.sandro.proxy.pureproxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DecoratorPatternTest {

    @Test
    void noDecorator() throws Exception {
        DecoratorPatternClient client = new DecoratorPatternClient(new RealComponent());

        client.execute();
    }

    @Test
    void decorator() throws Exception {
        DecoratorPatternClient client = new DecoratorPatternClient(new MessageDecorator(new RealComponent()));

        client.execute();
    }

    @Test
    void decoratorChain() throws Exception {
        DecoratorPatternClient client = new DecoratorPatternClient(new TimeDecorator(new MessageDecorator(new RealComponent())));

        client.execute();
    }
}

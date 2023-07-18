package com.sandro.proxy;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.part3.trace.logtrace.ThreadLocalLogTrace;
import com.sandro.proxy.config.v1_proxy.ConcreteProxyConfig;
import com.sandro.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)
//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
@Import(DynamicProxyBasicConfig.class)
@SpringBootApplication(scanBasePackages = "com.sandro.proxy.app")
public class ProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace trace() {
        return new ThreadLocalLogTrace();
    }
}

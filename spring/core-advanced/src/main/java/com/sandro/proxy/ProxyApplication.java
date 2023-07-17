package com.sandro.proxy;

import com.sandro.proxy.config.AppV1Config;
import com.sandro.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)
@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "com.sandro.proxy.app")
public class ProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}

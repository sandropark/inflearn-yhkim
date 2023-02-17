package com.sandro.typeconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sandro.typeconverter.type.IpPort;

@RestController
public class HelloController {

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/ip-port")
    public IpPort ipPort(@RequestParam IpPort ipPort) {
        return ipPort;
    }
}

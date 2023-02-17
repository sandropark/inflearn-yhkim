package com.sandro.jpashop2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")        // 메서드 상관없이 매핑
    public String home() {
        log.info("home controller");
        return "home";
    }
}

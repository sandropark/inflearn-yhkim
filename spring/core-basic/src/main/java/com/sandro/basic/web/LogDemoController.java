package com.sandro.basic.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sandro.basic.common.MyLogger;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @GetMapping("log-demo")
    public String logDemo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        myLogger.setRequestUrl(requestURI);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}

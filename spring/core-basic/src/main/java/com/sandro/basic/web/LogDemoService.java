package com.sandro.basic.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sandro.basic.common.MyLogger;

@RequiredArgsConstructor
@Service
public class LogDemoService {

    private final MyLogger myLogger;

    public void logic(String id) {
        myLogger.log("service id = " + id);
    }
}

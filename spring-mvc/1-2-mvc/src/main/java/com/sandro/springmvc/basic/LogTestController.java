package com.sandro.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j  // 아래 주석친 코드를 롬복으로 사용할 수 있다.
public class LogTestController {

//    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest() {

        log.trace("sandro");
        log.debug("sandro");
        log.info("sandro");
        log.warn("sandro");
        log.error("sandro");

        return "ok";
    }

}

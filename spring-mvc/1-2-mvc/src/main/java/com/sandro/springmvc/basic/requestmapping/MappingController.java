package com.sandro.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
@Slf4j
public class MappingController {

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    // 경로 변수
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    // 다중 경로 변수
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String multiMappingPath(
            @PathVariable String userId,
            @PathVariable String orderId
    ) {
        log.info("multiMappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    // 파라미터 조건 매핑
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    // 헤더 조건 매핑
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    // 미디어타입 조건 매핑 - http 요청 Content-Type
    @PostMapping(value = "/mapping-consume", consumes = APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    // 미디어타입 조건 매핑 - http 요청 Accept
    @PostMapping(value = "/mapping-produce", produces = TEXT_HTML_VALUE)
    public String mappingProduce() {
        log.info("mappingProduce");
        return "ok";
    }

}

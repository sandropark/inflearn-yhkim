package com.sandro.springmvc.basic.response;

import com.sandro.springmvc.basic.HelloData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyStringV1(HttpServletResponse response) throws IOException {
        response.getWriter()
                .write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyStringV2() {
        return new ResponseEntity<>("ok", OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyStringV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1()
    {
        HelloData helloData = new HelloData();
        helloData.setUsername("sandro");
        helloData.setAge(15);

        return new ResponseEntity<>(helloData, OK);
    }

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2()
    {
        HelloData helloData = new HelloData();
        helloData.setUsername("sandro");
        helloData.setAge(15);

        return helloData;
    }

}

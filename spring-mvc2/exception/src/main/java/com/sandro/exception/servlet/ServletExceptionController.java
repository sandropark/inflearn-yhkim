package com.sandro.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@Slf4j
@Controller
public class ServletExceptionController {

    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("DispatcherType = {}", request.getDispatcherType());
        response.sendError(SC_NOT_FOUND, "404 오류!");
    }

    @GetMapping("/error-400")
    public void error400(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("DispatcherType = {}", request.getDispatcherType());
        response.sendError(SC_BAD_REQUEST, "400 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(SC_INTERNAL_SERVER_ERROR);
    }

}

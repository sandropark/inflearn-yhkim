package com.sandro.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [start-line]
        response.setStatus(SC_OK);  // 응답 상태 코드 200

        // [response-headers]
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        // [Header 편의메서드]
        content(response);
        cookie(response);
        redirect(response);

        response.getWriter().write("ok");
    }

    private void content(HttpServletResponse response) {
        // response.setHeader("Content-Type", "text/plain;charset=utf-8");

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }

    private void cookie(HttpServletResponse response) {
//        response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");

        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
//        response.setStatus(SC_FOUND);
//        response.setHeader("Location", "basic/hello-form.html");

        response.sendRedirect("basic/hello-form.html");
    }
}

package com.sandro.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        printAllParams(request);

        printSinglePram(request);

        printSameKeyParams(request);

        response.getWriter().write("ok");
    }

    private void printAllParams(HttpServletRequest request) {
        System.out.println("[전체 파라미터 조회]");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> {
                    System.out.println(paramName + " = " + request.getParameter(paramName));
                });
        System.out.println();
    }

    private void printSinglePram(HttpServletRequest request) {
        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();
    }

    private void printSameKeyParams(HttpServletRequest request) {
        System.out.println("[키가 같은 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
        System.out.println();
    }
}

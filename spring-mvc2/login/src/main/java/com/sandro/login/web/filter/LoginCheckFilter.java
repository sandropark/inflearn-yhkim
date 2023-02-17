package com.sandro.login.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.sandro.login.web.session.SessionConst.LOGIN_MEMBER;

@Slf4j
public class LoginCheckFilter implements Filter {

    // 인증 체크를 하지 않는 url을 정의한다.
    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();


        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) { // 사용자가 요청한 URL이 인증이 필요한 URL인지 확인한다.
                log.info("인증 체크 로직 실행 {}", requestURI);

                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {    // 세션을 확인해서 인증된 사용자인지 확인한다.
                    log.info("미인증 사용자 요청 {}", requestURI);

                    // 로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI); // 로그인을 마치고 사용자가 요청했던 url로 다시 redirect 시켜준다.
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }

    }

    /**
     * 화이트 리스트인 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }


}

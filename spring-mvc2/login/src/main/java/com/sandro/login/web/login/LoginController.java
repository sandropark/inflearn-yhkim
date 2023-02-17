package com.sandro.login.web.login;

import com.sandro.login.domain.login.LoginService;
import com.sandro.login.domain.member.Member;
import com.sandro.login.domain.member.MemberRepository;
import com.sandro.login.web.session.SessionConst;
import com.sandro.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form) {
        return "login/loginForm";
    }

    /**
     * V1 : 쿠키를 사용하는 로그인
     */
//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login/loginForm";
        }

        // 로그인 성공처리

        /** 쿠키
         * 쿠키에 시간 정보를 주지 않으면 세션 쿠키가 된다. 브라우저 종료시에 만료된다. */
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

    /**
     * V2 : 세션을 사용하는 로그인
     */
//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login/loginForm";
        }

        // 로그인 성공처리

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

    /**
     * V3 : 서블릿 HttpSession 을 사용하는 로그인
     */
//    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login/loginForm";
        }

        // 로그인 성공처리
        HttpSession session = request.getSession(); // 파라미터 기본값=true 세션이 있으면 기존 세션을, 없으면 생성해서 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);   // 로그인 회원 정보 보관

        return "redirect:/";
    }

    /**
     * V4 : 서블릿 HttpSession 을 사용하는 로그인. redirect 적용
     */
    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login/loginForm";
        }

        // 로그인 성공처리
        HttpSession session = request.getSession(); // 파라미터 기본값=true 세션이 있으면 기존 세션을, 없으면 생성해서 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);   // 로그인 회원 정보 보관

        return "redirect:" + redirectURL;
    }

    /**
     * V1 : 쿠키를 사용하는 로그아웃
     */
//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * V2 : 세션을 사용하는 로그아웃
     */
//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    /**
     * V3 : 세션을 사용하는 로그아웃
     */
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // 있는 세션을 무효하는 게 목적이기 때문에 파라미터를 false해서 세션이 없어도 생성하지 않는다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}



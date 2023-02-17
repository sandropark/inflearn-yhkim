package com.sandro.login.web;

import com.sandro.login.domain.member.Member;
import com.sandro.login.domain.member.MemberRepository;
import com.sandro.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.sandro.login.web.session.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        // 쿠키 검증 로직
        if (memberId == null) {
            return "home";
        }
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        // 성공 로직
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     * V2 : 세션을 사용하는 루트페이지
     */
//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);
        if (member == null) { return "home"; }

        // 성공 로직
        model.addAttribute("member", member);
        return "loginHome";
    }

    /**
     * V3 : 세션을 사용하는 루트페이지
     */
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        // 세션에 저장된 회원 조회가 목적이기 때문에 세션이 없다면 생성하지 않는다.
        HttpSession session = request.getSession(false);
        if (session == null) { return "home"; } // 세션이 없다면 로그인 한 적 없는 사용자라는 뜻

        Member member = (Member) session.getAttribute(LOGIN_MEMBER);
        if (member == null) { return "home"; }  // 세션이 있어도 회원 정보가 없다면 로그인 하지 않았다는 뜻

        // 성공 로직
        model.addAttribute("member", member);
        return "loginHome";
    }

    /**
     * V3Spring : 스프링 애너테이션으로 세션을 사용하는 루트페이지
     */
    @GetMapping("/")
    public String homeLoginV3Spring(
            // 세션을 생성해주지는 않는다. 조회하는 용도로만 사용할 수 있다.
            @SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember,
            Model model
    ) {
        //  세션에서 데이터를 직접 찾아서 주입해준다. 데이터가 존재하는지 확인만 하면 된다.
        if (loginMember == null) { return "home"; }

        // 성공 로직
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}

















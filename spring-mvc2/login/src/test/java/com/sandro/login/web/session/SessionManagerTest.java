package com.sandro.login.web.session;

import com.sandro.login.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @DisplayName("응답 -> 요청 -> 검증 과정을 테스트한다.")
    @Test
    void test() throws Exception {
        // 응답 - 세션을 생성해서 응답으로 전달한다.
        MockHttpServletResponse response = new MockHttpServletResponse();   // 스프링이 테스트를 위해서 Mock 객체를 제공한다.
        Member member = new Member();
        sessionManager.createSession(member, response);               // 세션 생성

        // 요청 - 세션을 담아서 요청을 보낸다.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 서버 - 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 서버 - 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }

}
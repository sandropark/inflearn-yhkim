package com.sandro.login.domain.login;

import com.sandro.login.domain.member.Member;
import com.sandro.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * 로그인 로직
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.isYourPassword(password))
                .orElse(null);
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
    }

}

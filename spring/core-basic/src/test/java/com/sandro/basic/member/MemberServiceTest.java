package com.sandro.basic.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sandro.basic.AppConfig;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    AppConfig appConfig = new AppConfig();

    MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = appConfig.memberService();
    }

    @Test
    void join() throws Exception {
        // Given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // When
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());

        // Then
        assertThat(findMember).isEqualTo(member);
    }



}
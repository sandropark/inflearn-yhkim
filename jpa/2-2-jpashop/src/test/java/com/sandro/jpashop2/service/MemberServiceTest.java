package com.sandro.jpashop2.service;

import com.sandro.jpashop2.domain.Address;
import com.sandro.jpashop2.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    Address address;
    Member member;

    @BeforeEach
    void setUp() {
        address = new Address("seoul", "12st", "12345");
        member = new Member("sandro", address);
    }

    @DisplayName("회원가입")
    @Test
    void join() throws Exception {
        // given
        memberService.join(member);

        // when
        Member foundMember = memberService.findOne(member.getId());

        // then
        assertThat(foundMember).isSameAs(member);
    }

    @DisplayName("예외테스트 : 같은 이름의 회원이 가입하면 예외발생")
    @Test
    void validate() throws Exception {
        // given
        memberService.join(member);

        // when & then
        assertThatThrownBy(() -> memberService.join(member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 이름의 회원이 존재합니다.");
    }

}
package com.sandro.jpashop.service;

import com.sandro.jpashop.domain.Member;
import com.sandro.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("salt");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("san");
        Member member2 = new Member();
        member2.setName("san");

        // when
        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2))  // 같은 이름이기 때문에 예외 발생해야 한다.
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void 중복_회원_예외_2() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("san");
        Member member2 = new Member();
        member2.setName("san");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }

}
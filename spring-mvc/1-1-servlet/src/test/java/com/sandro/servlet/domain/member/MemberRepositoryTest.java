package com.sandro.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void test() throws Exception {
        // Given
        Member member = new Member("sandro", 20);
        Member savedMember = memberRepository.save(member);

        // When & Then
        Member foundMember = memberRepository.findById(savedMember.getId());
        assertThat(foundMember).isSameAs(savedMember);

        List<Member> foundMembers = memberRepository.findAll();
        assertThat(foundMembers.size()).isEqualTo(1);
        assertThat(foundMembers).contains(member);
    }

}
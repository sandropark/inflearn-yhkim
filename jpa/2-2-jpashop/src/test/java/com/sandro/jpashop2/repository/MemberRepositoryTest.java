package com.sandro.jpashop2.repository;

import com.sandro.jpashop2.domain.Address;
import com.sandro.jpashop2.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    Address address;
    Member member;

    @BeforeEach
    void setUp() {
        address = new Address("seoul", "12st", "12345");
        member = new Member("sandro", address);

        memberRepository.save(member);
    }

    @DisplayName("회원가입")
    @Test
    void save() throws Exception {
        // given

        // when
        Member foundMember = memberRepository.findOne(member.getId());

        // then
        assertThat(foundMember).isSameAs(member);
    }

    @DisplayName("전체조회")
    @Test
    void findAll() throws Exception {
        // given

        // when
        List<Member> foundMembers = memberRepository.listAll();

        // then
        assertThat(foundMembers).hasSize(1);
        assertThat(foundMembers).contains(member);
    }

    @DisplayName("이름으로 조회")
    @Test
    void findByName() throws Exception {
        // given

        // when
        List<Member> foundMembers = memberRepository.findAllByName(member.getName());

        // then
        assertThat(foundMembers).hasSize(1);
        assertThat(foundMembers).contains(member);
    }
}
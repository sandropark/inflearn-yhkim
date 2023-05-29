package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 인메모리 DB
 */
@Slf4j
@SpringBootTest
class MemberServiceV4_1Test {

    final String MEMBER_A = "memberA";
    final String MEMBER_B = "memberB";
    final String MEMBER_EX = "ex";
    final int seedMoney = 10000;
    int transferAmount = 1000;

    @Qualifier("memberRepositoryV4_2")
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberServiceV4_1 memberService;

    @SpringBootApplication(scanBasePackages = "com.sandro.jdbc")
    static class TestConfig {
    }

    @BeforeEach
    void setUp() throws SQLException {
        memberRepository.save(new Member(MEMBER_A, seedMoney));
        memberRepository.save(new Member(MEMBER_B, seedMoney));
        memberRepository.save(new Member(MEMBER_EX, seedMoney));
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.deleteAll();
    }

    @DisplayName("memberService는 Proxy 객체다.")
    @Test
    void aopCheck() throws Exception {
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    }

    @DisplayName("정상 이체")
    @Test
    void transfer() throws Exception {
        memberService.accountTransfer(MEMBER_A, MEMBER_B, transferAmount);

        Member foundFromMember = memberRepository.findById(MEMBER_A);
        Member foundToMember = memberRepository.findById(MEMBER_B);

        assertThat(foundFromMember.getMoney()).isEqualTo(seedMoney - transferAmount);
        assertThat(foundToMember.getMoney()).isEqualTo(seedMoney + transferAmount);
    }

    @DisplayName("이체 중 예외 발생")
    @Test
    void accountTransferEx() throws Exception {
        assertThatThrownBy(() -> memberService.accountTransfer(MEMBER_A, MEMBER_EX, transferAmount))
                .isInstanceOf(IllegalStateException.class);

        // Then
        Member foundFromMember = memberRepository.findById(MEMBER_A);
        Member foundToMember = memberRepository.findById(MEMBER_EX);

        assertThat(foundFromMember.getMoney()).isEqualTo(seedMoney);
        assertThat(foundToMember.getMoney()).isEqualTo(seedMoney);
    }

}

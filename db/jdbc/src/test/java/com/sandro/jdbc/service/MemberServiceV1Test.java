package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepositoryV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.sandro.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    static final String MEMBER_A = "memberA";
    static final String MEMBER_B = "memberB";
    static final String MEMBER_EX = "ex";

    MemberRepositoryV1 memberRepository;
    MemberServiceV1 memberService;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.deleteAll();
    }

    @DisplayName("정상 이체")
    @Test
    void transfer() throws Exception {
        int seedMoney = 10000;
        Member memberA = new Member(MEMBER_A, seedMoney);
        Member memberB = new Member(MEMBER_B, seedMoney);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        String aId = memberA.getMemberId();
        String bId = memberB.getMemberId();
        int transferAmount = 1000;
        memberService.accountTransfer(aId, bId, transferAmount);

        Member foundFromMember = memberRepository.findById(aId);
        Member foundToMember = memberRepository.findById(bId);

        assertThat(foundFromMember.getMoney()).isEqualTo(seedMoney - transferAmount);
        assertThat(foundToMember.getMoney()).isEqualTo(seedMoney + transferAmount);
    }

    @DisplayName("이체 중 예외 발생")
    @Test
    void accountTransferEx() throws Exception {
        int seedMoney = 10000;
        Member memberA = new Member(MEMBER_A, seedMoney);
        Member memberEx = new Member(MEMBER_EX, seedMoney);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        String aId = memberA.getMemberId();
        String exId = memberEx.getMemberId();
        int transferAmount = 1000;

        // When
        assertThatThrownBy(() -> memberService.accountTransfer(aId, exId, transferAmount))
                .isInstanceOf(IllegalStateException.class);

        // Then
        Member foundFromMember = memberRepository.findById(aId);
        Member foundToMember = memberRepository.findById(exId);

        assertThat(foundFromMember.getMoney()).isEqualTo(seedMoney - transferAmount);
        assertThat(foundToMember.getMoney()).isEqualTo(seedMoney);
    }

}
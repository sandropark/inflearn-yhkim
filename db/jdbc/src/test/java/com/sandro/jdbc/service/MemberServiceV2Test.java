package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepositoryV2;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.sandro.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
class MemberServiceV2Test {

    static final String MEMBER_A = "memberA";
    static final String MEMBER_B = "memberB";
    static final String MEMBER_EX = "ex";

    MemberRepositoryV2 memberRepository;
    MemberServiceV2 memberService;

    @BeforeEach
    void setUp() {
        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
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

        assertThat(foundFromMember.getMoney()).isEqualTo(seedMoney);
        assertThat(foundToMember.getMoney()).isEqualTo(seedMoney);
    }

}
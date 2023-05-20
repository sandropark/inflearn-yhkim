package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepositoryV3;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.sandro.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberServiceV3_3Test {

    final String MEMBER_A = "memberA";
    final String MEMBER_B = "memberB";
    final String MEMBER_EX = "ex";
    final int seedMoney = 10000;
    int transferAmount = 1000;

    @Autowired
    MemberRepositoryV3 memberRepository;
    @Autowired
    MemberServiceV3_3 memberService;

    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestConfig {

        @Bean
        DataSource dataSource() {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(URL);
            dataSource.setUsername(USERNAME);
            dataSource.setPassword(PASSWORD);

            return dataSource;
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3(DataSource dataSource) {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3(MemberRepositoryV3 memberRepository) {
            return new MemberServiceV3_3(memberRepository);
        }
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

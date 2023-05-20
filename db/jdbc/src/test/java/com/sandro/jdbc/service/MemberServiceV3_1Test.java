package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepositoryV3;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;

import static com.sandro.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
class MemberServiceV3_1Test {

    static final String MEMBER_A = "memberA";
    static final String MEMBER_B = "memberB";
    static final String MEMBER_EX = "ex";

    static MemberRepositoryV3 memberRepository;
    static MemberServiceV3_1 memberService;

    int seedMoney = 10000;
    int transferAmount = 1000;
    Member memberA = new Member(MEMBER_A, seedMoney);
    Member memberB = new Member(MEMBER_B, seedMoney);
    Member memberEx = new Member(MEMBER_EX, seedMoney);

    @BeforeAll
    static void beforeAll() {
        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
    }

    @BeforeEach
    void setUp() throws SQLException {
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberEx);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.deleteAll();
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
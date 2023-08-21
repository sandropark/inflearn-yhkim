package com.sandro.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    /**
     * memberService    @Transaction:OFF
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON
     */
    @Test
    void outerTxOff_success() throws Exception {
        // Given
        String username = "outerTxOff_success";

        // When
        memberService.joinV1(username);

        // Then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.find(username)).isPresent();
    }

    /**
     * memberService    @Transaction:OFF
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON Exception
     */
    @Test
    void outerTxOff_fail() throws Exception {
        // Given
        String username = "로그예외_outerTxOff_fail";

        // When
        memberService.joinV2(username);

        // Then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.find(username)).isEmpty();
    }

    /**
     * memberService    @Transaction:ON
     * memberRepository @Transaction:OFF
     * logRepository    @Transaction:OFF
     */
    @Test
    void singleTx() throws Exception {
        // Given
        String username = "outerTxOff_success";

        // When
        memberService.joinV3(username);

        // Then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.find(username)).isPresent();
    }

    /**
     * memberService    @Transaction:ON
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON
     */
    @Test
    void outerTxOn_success() throws Exception {
        // Given
        String username = "outerTxOn_success";

        // When
        memberService.joinV4(username);

        // Then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.find(username)).isPresent();
    }

    /**
     * memberService    @Transaction:ON
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON Exception
     */
    @Test
    void outerTxOn_fail() throws Exception {
        // Given
        String username = "로그예외_outerTxOn_fail";

        // When
        assertThatThrownBy(() -> memberService.joinV4(username))
                .isInstanceOf(RuntimeException.class);

        // Then
        assertThat(memberRepository.findByUsername(username)).isEmpty();
        assertThat(logRepository.find(username)).isEmpty();
    }

    /**
     * memberService    @Transaction:ON
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON Exception
     */
    @Test
    void recoverEx_fail() throws Exception {
        // Given
        String username = "로그예외_recoverEx_fail";

        // When
        assertThatThrownBy(() -> memberService.joinV5(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        // Then
        assertThat(memberRepository.findByUsername(username)).isEmpty();
        assertThat(logRepository.find(username)).isEmpty();
    }

    /**
     * memberService    @Transaction:ON
     * memberRepository @Transaction:ON
     * logRepository    @Transaction:ON(REQUIRES_NEW) Exception
     */
    @Test
    void recoverEx_success() throws Exception {
        // Given
        String username = "로그예외_recoverEx_success";

        // When
        memberService.joinV6(username);

        // Then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.find(username)).isEmpty();
    }

}
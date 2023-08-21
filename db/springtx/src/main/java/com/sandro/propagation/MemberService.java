package com.sandro.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        logRepository.saveWithTx(logMessage);
        log.info("== logRepository 호출 종료 ==");
    }

    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        try {
            logRepository.saveWithTx(logMessage);
        } catch (RuntimeException e) {
            log.info("log 저장 실패. logMessage={}", logMessage.getMessage());
            log.info("정상 흐름 반환");
        }
        log.info("== memberRepository 호출 종료 ==");
    }

    @Transactional
    public void joinV3(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        logRepository.saveNoTx(logMessage);
        log.info("== logRepository 호출 종료 ==");
    }

    @Transactional
    public void joinV4(String username) {
        joinV1(username);
    }

    @Transactional
    public void joinV5(String username) {
        joinV2(username);
    }

    @Transactional
    public void joinV6(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        try {
            logRepository.saveWithNewTx(logMessage);
        } catch (RuntimeException e) {
            log.info("log 저장 실패. logMessage={}", logMessage.getMessage());
            log.info("정상 흐름 반환");
        }
        log.info("== memberRepository 호출 종료 ==");
    }

}

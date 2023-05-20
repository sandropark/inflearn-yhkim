package com.sandro.jdbc.service;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.MemberRepositoryV4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 탬플릿
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceV4 {

    private final MemberRepositoryV4 memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) throw new IllegalStateException("이체중 예외 발생");
    }

}

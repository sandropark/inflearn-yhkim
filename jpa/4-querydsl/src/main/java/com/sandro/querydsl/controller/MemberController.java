package com.sandro.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sandro.querydsl.dto.MemberSearchCondition;
import com.sandro.querydsl.dto.MemberTeamDto;
import com.sandro.querydsl.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberRepository.search(condition);
    }
}

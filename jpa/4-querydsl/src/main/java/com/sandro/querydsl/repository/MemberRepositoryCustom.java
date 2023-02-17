package com.sandro.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sandro.querydsl.dto.MemberSearchCondition;
import com.sandro.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPage(MemberSearchCondition condition, Pageable pageable);

}

package com.sandro.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    // 회원명, 팀명, 나이(ageGoe, ageLoe)
    // goe : Greater or Equal
    // loe : Lower or Equal

    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}

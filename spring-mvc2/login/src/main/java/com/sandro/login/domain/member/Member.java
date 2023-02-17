package com.sandro.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member() {}

    public boolean isYourLoginId(String loginId) {
        return this.loginId.equals(loginId);
    }

    public boolean isYourPassword(String password) {
        return this.password.equals(password);
    }
}

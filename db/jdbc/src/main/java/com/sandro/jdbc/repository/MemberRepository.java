package com.sandro.jdbc.repository;

import com.sandro.jdbc.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);

    public void deleteAll();
}

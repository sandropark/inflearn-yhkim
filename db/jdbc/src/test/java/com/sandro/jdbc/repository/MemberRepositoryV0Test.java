package com.sandro.jdbc.repository;

import com.sandro.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws Exception {
        Member member = new Member("memberV1", 10000);
        repository.save(member);

        Member foundMember = repository.findById(member.getMemberId());

        assertThat(foundMember).isEqualTo(member);
    }

}
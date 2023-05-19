package com.sandro.jdbc.repository;

import com.sandro.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws Exception {
        // save
        Member member = new Member("memberV1", 10000);
        repository.save(member);

        // findById
        Member foundMember = repository.findById(member.getMemberId());

        assertThat(foundMember).isEqualTo(member);

        // Update
        int updatedMoney = 20000;
        repository.update(member.getMemberId(), updatedMoney);

        Member updatedMember = repository.findById(member.getMemberId());

        assertThat(updatedMember.getMoney()).isEqualTo(updatedMoney);

        // Delete
        repository.delete(member.getMemberId());

        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}
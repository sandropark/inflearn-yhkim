package com.sandro.datajpa.repository;

import com.sandro.datajpa.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;
    @Autowired MemberRepository memberRepository;
    Member member1;
    Member member2;

    @BeforeEach
    void setUp() {
        member1 = new Member("sandro", 20);
        member2 = new Member("salt", 20);
    }

    @DisplayName("JPA Repository Test")
    @Test
    void jpaRepository() throws Exception {
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회
        Member foundMember = memberJpaRepository.findById(member1.getId()).get();
        assertThat(foundMember).isSameAs(member1);

        // 카운트 검증
        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 전체 조회
        List<Member> foundMembers = memberJpaRepository.findAll();
        assertThat(foundMembers.size()).isEqualTo(2);

        //  삭제
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        Long countAfterDelete = memberJpaRepository.count();
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("Data JPA Repository Test")
    @Test
    void dataJpaRepository() throws Exception {
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회
        Member foundMember = memberRepository.findById(member1.getId()).get();
        assertThat(foundMember).isSameAs(member1);

        // 카운트 검증
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 전체 조회
        List<Member> foundMembers = memberRepository.findAll();
        assertThat(foundMembers.size()).isEqualTo(2);

        //  삭제
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long countAfterDelete = memberRepository.count();
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void paging() throws Exception {
        int age = 20;
        int offset = 0;
        int limit = 3;

        Member member3 = new Member("sandro", age);
        Member member4 = new Member("sandro", age);
        Member member5 = new Member("sandro", age);
        Member member6 = new Member("sandro", age);
        Member member7 = new Member("sandro", 14);
        Member member8 = new Member("sandro", 15);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);
        memberJpaRepository.save(member6);
        memberJpaRepository.save(member7);
        memberJpaRepository.save(member8);

        Long totalCount = memberJpaRepository.totalCount(age);
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);

        assertThat(totalCount).isEqualTo(6L);
        assertThat(members.size()).isEqualTo(3L);
    }

    @Test
    void bulkUpdate() throws Exception {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 15));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 25));
        memberJpaRepository.save(new Member("member5", 30));

        int resultCount = memberJpaRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }

}

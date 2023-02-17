package com.sandro.querydsl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.sandro.querydsl.dto.MemberSearchCondition;
import com.sandro.querydsl.dto.MemberTeamDto;
import com.sandro.querydsl.entity.Member;
import com.sandro.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.sandro.querydsl.entity.QMember.member;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;

    Member member1;
    Member member2;
    Member member3;
    Member member4;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        member1 = new Member("member1", 10, teamA);
        member2 = new Member("member2", 20, teamA);
        member3 = new Member("member3", 30, teamB);
        member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void basicTest() throws Exception {
        Member member = new Member("sandro", 12);
        memberRepository.save(member);

        Member foundMember = memberRepository.findById(member.getId()).get();
        assertThat(foundMember).isSameAs(member);

        List<Member> members = memberRepository.findAll();
        assertThat(members).contains(member);

        List<Member> byUsername = memberRepository.findByUsername(member.getUsername());
        assertThat(byUsername).containsExactly(member);
    }

    @Test
    void searchTest() throws Exception {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(15);
        condition.setAgeLoe(35);
        condition.setTeamName("TeamB");

        List<MemberTeamDto> result = memberRepository.search(condition);

        assertThat(result).extracting("username").containsExactly("member3");
    }

    @Test
    void searchPage() {
        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDto> result = memberRepository.searchPage(condition, pageRequest);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent())
                .extracting("username")
                .containsExactly(member1.getUsername(), member2.getUsername(), member3.getUsername());
    }

    @Test
    void querydslPredicateExecutor() {
        Iterable<Member> members = memberRepository.findAll(member.age.between(10, 30));
        for (Member m : members) {
            System.out.println("m = " + m);
        }
    }

}
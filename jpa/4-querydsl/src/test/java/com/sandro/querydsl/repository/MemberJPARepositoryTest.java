package com.sandro.querydsl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.sandro.querydsl.dto.MemberSearchCondition;
import com.sandro.querydsl.dto.MemberTeamDto;
import com.sandro.querydsl.entity.Member;
import com.sandro.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJPARepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberJPARepository memberJPARepository;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void basicTest() throws Exception {
        Member member = new Member("sandro", 12);
        memberJPARepository.save(member);

        Member foundMember = memberJPARepository.findById(member.getId()).get();
        assertThat(foundMember).isSameAs(member);

        List<Member> members = memberJPARepository.findAll_Querydsl();
        assertThat(members).contains(member);

        List<Member> byUsername = memberJPARepository.findByUsername_Querydsl(member.getUsername());
        assertThat(byUsername).containsExactly(member);
    }

    @Test
    void searchTest() throws Exception {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(15);
        condition.setAgeLoe(35);
        condition.setTeamName("TeamB");

        List<MemberTeamDto> result = memberJPARepository.search(condition);

        assertThat(result).extracting("username").containsExactly("member3");
    }

    @Test
    void searchTest2() throws Exception {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setTeamName("TeamB");

        List<MemberTeamDto> result = memberJPARepository.search(condition);

        assertThat(result).extracting("username").containsExactly("member3", "member4");
    }

}
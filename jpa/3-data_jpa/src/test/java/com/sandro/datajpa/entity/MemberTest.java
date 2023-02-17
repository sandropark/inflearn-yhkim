package com.sandro.datajpa.entity;

import com.sandro.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
class MemberTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;

    @Test
    void test() throws Exception {
        Team teamTiger = new Team("tiger");
        Team teamLion = new Team("lion");
        em.persist(teamTiger);
        em.persist(teamLion);

        Member member1 = new Member("sandro", 20, teamTiger);
        Member member2 = new Member("pepper", 19, teamTiger);
        Member member3 = new Member("salt", 18, teamLion);
        Member member4 = new Member("sugar", 17, teamLion);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    @Test
    void jpaEventBaseEntity() throws Exception {
        Member member = new Member("member1");
        memberRepository.save(member);

        member.setUsername("member2");

        em.flush();
        em.clear();

        Member foundMember = memberRepository.findById(member.getId()).get();
        System.out.println("foundMember.getCreatedAt() = " + foundMember.getCreatedDate());
        System.out.println("foundMember.getModifiedAt() = " + foundMember.getLastModifiedDate());
        System.out.println("foundMember.getCreatedBy() = " + foundMember.getCreatedBy());
        System.out.println("foundMember.getLastModifiedBy() = " + foundMember.getLastModifiedBy());
        System.out.println("foundMember.getUsername() = " + foundMember.getUsername());
    }
}
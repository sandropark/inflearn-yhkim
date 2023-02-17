package com.sandro.datajpa.repository;

import com.sandro.datajpa.dto.MemberDto;
import com.sandro.datajpa.entity.Member;
import com.sandro.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;

    @Test
    void findByUsernameAndAgeGreaterThan() throws Exception {
        Member member1 = new Member("sandro", 10);
        Member member2 = new Member("sandro", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("sandro", 13);

        assertThat(members).containsExactly(member2);
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void findMember() throws Exception {
        Member member1 = new Member("sandro", 10);
        Member member2 = new Member("sandro", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findMember("sandro", 10);

        assertThat(members).containsExactly(member1);
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void findUsernameList() throws Exception {
        Member member1 = new Member("sandro", 10);
        Member member2 = new Member("salt", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> usernameList = memberRepository.findUsernameList();

        assertThat(usernameList).containsExactly(member1.getUsername(), member2.getUsername());
    }

    @Test
    void findMemberDto() throws Exception {
        Team team = new Team("lion");
        teamRepository.save(team);

        Member member = new Member("sandro", 10);
        member.changeTeam(team);
        memberRepository.save(member);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        assertThat(memberDto.get(0).getId()).isEqualTo(member.getId());
        assertThat(memberDto.get(0).getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void findByNames() throws Exception {
        Member member1 = new Member("sandro", 10);
        Member member2 = new Member("salt", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByNames(List.of("sandro", "salt"));

        assertThat(members).containsExactly(member1, member2);
    }

    @Test
    void returnType() throws Exception {
        Member member1 = new Member("sandro", 10);
        Member member2 = new Member("salt", 15);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findListByUsername("sandro");
        assertThat(members).containsExactly(member1);

        Member foundMember = memberRepository.findMemberByUsername("sandro");
        assertThat(foundMember).isSameAs(member1);

        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("sandro");
        assertThat(optionalMember.get()).isSameAs(member1);
    }

    @Test
    void paging() throws Exception {
        int age = 20;

        Member member3 = new Member("sandro", age);
        Member member4 = new Member("sandro", age);
        Member member5 = new Member("sandro", age);
        Member member6 = new Member("sandro", age);

        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");

        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        List<Member> content = page.getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(4);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    void callCustom() throws Exception {
        List<Member> members = memberRepository.findMemberCustom();
    }

    @Test
    void bulkUpdate() throws Exception {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 15));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 25));
        memberRepository.save(new Member("member5", 30));

        int resultCount = memberRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy() throws Exception {
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        teamRepository.save(team1);
        teamRepository.save(team2);

        Member member1 = new Member("member1", 10, team1);
        Member member2 = new Member("member2", 10, team2);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

    }
    
}

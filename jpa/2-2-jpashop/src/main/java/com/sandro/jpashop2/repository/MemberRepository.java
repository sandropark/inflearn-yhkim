package com.sandro.jpashop2.repository;

import com.sandro.jpashop2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    JPA로 em 주입 받기
//    @PersistenceContext     // JPA 애노테이션이다.
//    private EntityManager em;

    // 스프링 DATA JPA를 사용하면 이렇게 생성자 주입으로 바로 사용할 수 있다.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    // jpql을 사용해서 전체 조회 후 리스트로 반환
    public List<Member> listAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAllByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}

package com.sandro.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        // 1. Persistence로부터 emf -> em을 꺼낸다.
        // persistentce.xml에서 설정한 unitName을 넣는다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // 2. transaction을 생성하고 실행.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 3. 엔티티 객체를 생성
            Member member = new Member();
            member.setId(1L);
            member.setName("salt");

            // 4. db에 저장. (영속성 객체로 변환)
            em.persist(member);

            // 5. tx종료 (db에 반영)
            tx.commit();
        } catch (Exception e) {
            // 6. 예외 발생시 transaction 롤백
            tx.rollback();
        } finally {
            // 7. em 종료
            em.close();
        }
        // 8. emf 종료
        emf.close();
    }
}

package com.sandro.hellojpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JpaMainTest {
    // TODO : 설정마치기

    // emf는 애플리케이션 전체에서 1개만 생성된다.
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    @Test
    void 조회() throws Exception {
        // em은 쓰레드간에 공유하지 않고 사용하고 버린다.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member(1L, "salt");
            em.persist(member);

            Member foundMember = em.find(Member.class, 1L);
            tx.commit();

            assertThat(foundMember).hasFieldOrPropertyWithValue("name", "salt");
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Test
    void 수정() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /** jpa를 통해서 entity를 가져오면 jpa는 해당 entity를 관리하고 있기 때문에 엔티티에 수정사항이 생기면
             * tx.commit 시점에 변경사항을 감지해서 update 쿼리를 날린다. */
            Member member = new Member(1L, "sugar");
            em.persist(member);

            Member foundMember = em.find(Member.class, 1L);
            String previousName = foundMember.getName();
            foundMember.setName("sugar");

            Member updatedMember = em.find(Member.class, 1L);
            String updatedName = updatedMember.getName();

            foundMember.setName("salt");
            tx.commit();

            assertThat(previousName).isEqualTo("salt");
            assertThat(updatedName).isEqualTo("sugar");
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @DisplayName("Jpql을 사용해서 전체 데이터 조회")
    @Test
    void jpql() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // given
            // 현재 db에 데이터가 하나 저장되어 있다.
            // 엔티티 하나 더 저장
            em.persist(new Member(2L, "pepper"));

            // when
            // jpql로 전체 데이터 불러오기
            List<Member> foundMembers = em.createQuery("select m from Member as m", Member.class)
                            .getResultList();
            tx.commit();
            String[] foundNames = foundMembers.stream()
                    .map(Member::getName)
                    .toArray(String[]::new);

            // then
            assertThat(foundMembers.size()).isEqualTo(2);
            assertThat(foundNames).contains("sandro", "pepper");

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @DisplayName("엔티티 생명주기")
    @Test
    void 영속성_테스트() throws Exception {
        Member member = new Member(10L, "apple");   // 객체 생성 : 비영속

        EntityManager em = emf.createEntityManager();
        em.persist(member);                                     // 영속화
    }

    @DisplayName("영속성 : 1차 캐시 확인")
    @Test
    void 영속성_테스트_1차캐시() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member(1L, "salt");
        em.persist(member);

        Member foundMember = em.find(Member.class, 1L);
        Member foundMember1 = em.find(Member.class, 1L);

        tx.commit();
        /* 같은 객체를 2번 find하고 있다. 하지만 로그를 확인해보면 select쿼리는 1번만 날린다.
         * 그 이유는 다음과 같다.
         * em은 db에서 엔티티를 가져오면서 1차 캐시에 저장해둔다.
         * 그리고 find로 요청이 들어오면 먼저 1차 캐시를 확인해서 객체가 있다면 db까지 조회하지 않고
         * 엔티티를 반환해준다.*/
        assertThat(foundMember).hasFieldOrPropertyWithValue("name", "salt");
        em.close();
    }

    @DisplayName("영속성 : 영속 엔티티 동일성 보장 ")
    @Test
    void 영속성_테스트_동일성() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member foundMember = em.find(Member.class, 1L);
        Member foundMember1 = em.find(Member.class, 1L);

        // 1차 캐시를 사용하기 때문에 가능하다.
        assertThat(foundMember).isEqualTo(foundMember1);
        tx.commit();
        em.close();
    }

    @DisplayName("영속성 : dirty check (변경 감지)")
    @Test
    void 영속성_테스트_변경감지() throws Exception {
        // 1. transaction 을 사용하지 않고 테스트
        EntityManager em = emf.createEntityManager();

        Member foundMember = em.find(Member.class, 1L);
        foundMember.setName("sandro");

        em.close();

        EntityManager em2 = emf.createEntityManager();

        Member modifiedFoundMember = em2.find(Member.class, 1L);
        // transaction 을 사용하지 않아서 변경사항이 반영되지 않았다.
        assertThat(modifiedFoundMember).hasFieldOrPropertyWithValue("name", "salt");

        em2.close();

        // 2. transaction 사용
        EntityManager em3 = emf.createEntityManager();
        EntityTransaction tx = em3.getTransaction();
        tx.begin();

        Member foundMember2 = em3.find(Member.class, 1L);
        foundMember2.setName("sandro");

        tx.commit();
        em3.close();
        // DB에 업데이트가 잘 되었다.

        EntityManager em4 = emf.createEntityManager();

        Member modifiedFoundMember2 = em4.find(Member.class, 1L);
        assertThat(modifiedFoundMember2).hasFieldOrPropertyWithValue("name", "sandro");

        em4.close();

        /* 영속성 컨텍스트안에 1차 캐시에는 키/엔티티/스냅샷이 있다. 스냅샷은 엔티티가 영속성 컨텍스트에 들어온 시점을 저장해두고
         * 커밋하는 시점에 flush를 하게된다. flush를 하는 시점에 엔티티와 스냅샷을 비교해서 변경사항이 있다면 쿼리를 수정하고
         * DB에 flush하고 commit하게 된다. */
    }

    @DisplayName("영속성 : flush")
    @Test
    void 영속성_테스트_flush() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member cocoa = new Member(3L, "cocoa");
        em.persist(cocoa);

        List<Member> members = em.createQuery("select m from Member as m", Member.class).getResultList();

        /* 객체를 persist하면 DB에 저장되는 건 아니다.
           하지만 쿼리를 날려서 전체 조회했을 때 persists만한 객체를 가져올 수 있었다.
           그 이유는 jpql을 실행하면 먼저 자동으로 flush를 하기 때문이다.
           flush를 하면 persist된 객체를 sql을 날려서 db에 저장되고 그 후 전체 데이터를 조회해 온다. */
        assertThat(members).contains(cocoa);

        em.remove(cocoa);
        tx.commit();
        em.close();
    }

    @DisplayName("기본키 전략 : SEQUENCE")
    @Test
    void 기본키_전략() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        MemberSequence member1 = new MemberSequence("salt");
        MemberSequence member2 = new MemberSequence("sugar");
        MemberSequence member3 = new MemberSequence("pepper");

        System.out.println("======================");

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        System.out.println("======================");

        tx.commit();
        em.close();

        /* SEQUENCE 전략은 DB의 시퀀스를 사용해서 id를 생성하는 전략이다.
        * 객체를 생성해서 persist하면 id가 없기 때문에 시퀀스를 호출해서 id를 받아온다.
        * 만약 매번 시퀀스를 호출한다면 성능이슈가 생길 수 있다. 해결법으로 한 번에 50개의 id를 생성해서
        * 가져와서 사용할 수 있다. 쿼리를 보면 처음에만 시퀀스를 호출하고 다음 2개는 시퀀스를 호출하지 않고 id를 지정한다. */
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }
}
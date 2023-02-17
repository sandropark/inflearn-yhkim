package com.sandro.hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // table과 매칭되는 클래스 지정
//@Table(name = "USER")   // 클래스와 테이블의 이름이 다른 경우 테이블의 이름을 잡아줄 수 있다.
public class Member {


    @Id     // jpa에게 pk가 뭔지 알려줘야 한다.
    private Long id;
//    @Column(name = "user_name") // 필드와 컬럼의 이름이 다른 경우 컬럼명을 잡아줄 수 있다.
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

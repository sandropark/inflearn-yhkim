package com.sandro.jpashop2.domain;

import com.sandro.jpashop2.controller.MemberForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")     // 양방향 매핑
    private final List<Order> orders = new ArrayList<>();

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Member(MemberForm memberForm) {
        this.name = memberForm.getName();
        this.address = new Address(memberForm);
    }

    public Member(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}

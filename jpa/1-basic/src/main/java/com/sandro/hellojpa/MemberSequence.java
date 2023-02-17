package com.sandro.hellojpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR",
                    sequenceName = "MEMBER_SEQ",
                    initialValue = 1, allocationSize = 50)
public class MemberSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
    private String name;

    public MemberSequence() {
    }

    public MemberSequence(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

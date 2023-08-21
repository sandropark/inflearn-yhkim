package com.sandro.propagation;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;

    public Member(String username) {
        this.username = username;
    }
}

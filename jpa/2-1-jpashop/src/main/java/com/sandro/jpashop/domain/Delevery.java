package com.sandro.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delevery {
    @Id
    @GeneratedValue
    @Column(name = "delevery_id")
    private Long id;

    @OneToOne(mappedBy = "delevery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)  // 기본값이 ordinal이지만 순서가 바뀔 수도 있기 때문에 STRING을 사용한다.
    private DeleveryStatus status; // READY, COMP
}

package com.sandro.jpashop2.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")        // 테이블에서 상속관계 매핑되는 값이다.
@Getter
public class Album extends Item {
    private String artist;
    private String etc;
}

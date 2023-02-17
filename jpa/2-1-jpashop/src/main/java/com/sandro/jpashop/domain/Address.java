package com.sandro.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    /*
    값 타입은 변경이 불가능 해야한다. 생성자에서만 값을 설정할 수 있게 만든다.
    엔티티나 임베디드 타입은 기본 생성자를 열어둬야하고 public 이나 protected로 지정할 수 있다.
    외부에서 기본생성자에 접근하기 힘들도록 protected로 설정한다.
     */

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

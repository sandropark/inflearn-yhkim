package com.sandro.jpashop2.domain;

import com.sandro.jpashop2.controller.MemberForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@AllArgsConstructor     // 불변 객체로 만들기 위해서 Setter를 사용하지 않고 생성자에서만 값을 초기화 할 수 있다.
@NoArgsConstructor(access = PROTECTED)      // JPA가 기본 생성자를 필요로 하기 때문에 protected로 추가
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(MemberForm memberForm) {
        this.city = memberForm.getCity();
        this.street = memberForm.getStreet();
        this.zipcode = memberForm.getZipcode();
    }
}

package com.sandro.jpashop2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private DeliveryStatus status;

    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void complete() {
        this.status = DeliveryStatus.COMP;
    }
}

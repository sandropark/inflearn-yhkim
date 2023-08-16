package com.sandro.order;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Data
@RequiredArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue
    private Long id;

    private String username;
    private String payStatus;
}

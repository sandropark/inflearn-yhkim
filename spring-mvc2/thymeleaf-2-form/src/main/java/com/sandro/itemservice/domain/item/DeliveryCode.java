package com.sandro.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * FAST : 빠른 배송
 * NORMAL : 일반 배송
 * SLOW : 느린 배송
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName;
    private static final List<DeliveryCode> list = List.of(
            new DeliveryCode("FAST", "빠른 배송"),
            new DeliveryCode("NORMAL", "일반 배송"),
            new DeliveryCode("SLOW", "느린 배송")
    );

    public static List<DeliveryCode> getList() {
        return list;
    }
}

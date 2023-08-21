package com.sandro.propagation;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Log {

    @Id @GeneratedValue
    private Long id;
    private String message;

    public Log(String message) {
        this.message = message;
    }
}

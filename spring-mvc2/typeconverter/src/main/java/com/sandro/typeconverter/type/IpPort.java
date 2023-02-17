package com.sandro.typeconverter.type;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IpPort {

    private String ip;
    private int port;

}

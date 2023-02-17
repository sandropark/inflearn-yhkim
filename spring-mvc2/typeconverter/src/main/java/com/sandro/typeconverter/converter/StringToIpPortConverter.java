package com.sandro.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import com.sandro.typeconverter.type.IpPort;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert(String source) {
        log.info("convert source = {}", source);
//        "127.0.0.1:8080"
        String[] splitByColon = source.split(":");
        String ip = splitByColon[0];
        int port = Integer.parseInt(splitByColon[1]);
        return new IpPort(ip, port);
    }
}

package com.sandro.typeconverter.converter;

import org.junit.jupiter.api.Test;
import com.sandro.typeconverter.type.IpPort;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {

    @Test
    void stringToIntegerTest() throws Exception {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");

        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToStringTest() throws Exception {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);

        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() throws Exception {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        IpPort ipPort = converter.convert("127.0.0.1:8080");

        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void ipPortToString() throws Exception {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        String result = converter.convert(new IpPort("127.0.0.1", 8080));

        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

}
package com.sandro.typeconverter.formatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @ParameterizedTest
    @CsvSource(value = {"1,000: 1000", "10,000: 10000", "567,023: 567023"}, delimiterString = ":")
    void numberFormatter(String num, int expected) throws Exception {
        // when
        String[] splitted = num.split(",");
        int length = splitted.length-1;
        int result = 0;
        for (String s : splitted) {
            int value = (int) Math.pow(1000, length--);
            result += value > 0 ? Integer.parseInt(s) * value : Integer.parseInt(s);
        }
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void parse() throws Exception {
        // given
        Number parse = (Number) formatter.parse("1,000", Locale.KOREA);

        // when
        assertThat(parse).isEqualTo(1000L);
    }

    @Test
    void format() {
        String print = formatter.print(1000, Locale.KOREA);

        assertThat(print).isEqualTo("1,000");
    }

}

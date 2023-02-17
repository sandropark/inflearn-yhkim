package com.sandro.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired MessageSource ms;

    @Test
    void test() throws Exception {
        String result = ms.getMessage("hello", null, null);

        assertThat(result).isEqualTo("hello");
    }

    @Test
    void notFoundMessageCode() throws Exception {
        assertThatThrownBy(() -> ms.getMessage(null, null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() throws Exception {
        String result = ms.getMessage(null, null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() throws Exception {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("hello Spring");
    }

    @Test
    void defaultLang() throws Exception {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("hello");
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

    @Test
    void koLang() throws Exception {
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREAN)).isEqualTo("안녕");
    }

}

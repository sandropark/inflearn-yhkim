package com.sandro.typeconverter.formatter;

import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

    @Test
    void test() throws Exception {
        // given
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        // 컨버터 등록
//        conversionService.addConverter(new StringToIntegerConverter());
        // 포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        String result1 = conversionService.convert(1000, String.class);
        Integer result2 = conversionService.convert("1,000", Integer.class);

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }
}

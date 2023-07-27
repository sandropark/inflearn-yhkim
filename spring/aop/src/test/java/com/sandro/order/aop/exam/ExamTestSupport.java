package com.sandro.order.aop.exam;

import com.sandro.order.aop.exam.aop.RetryAspect;
import com.sandro.order.aop.exam.aop.TraceAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
class ExamTestSupport {

    @Autowired
    ExamService examService;

    @SpringBootApplication(scanBasePackages = "com.sandro.order.aop.exam")
    protected static class Context {}

}
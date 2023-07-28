package com.sandro.aop.exam;

import com.sandro.aop.exam.aop.RetryAspect;
import com.sandro.aop.exam.aop.TraceAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
class ExamTestSupport {

    @Autowired
    ExamService examService;

}
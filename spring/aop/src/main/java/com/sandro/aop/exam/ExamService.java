package com.sandro.aop.exam;

import com.sandro.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final ExamRepository repository;

    @Trace
    public void request(String itemId) {
        repository.save(itemId);
    }
}

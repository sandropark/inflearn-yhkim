package com.sandro.part3.trace.template;

import com.sandro.part3.trace.TraceStatus;
import com.sandro.part3.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            // 로직 호출
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();

}


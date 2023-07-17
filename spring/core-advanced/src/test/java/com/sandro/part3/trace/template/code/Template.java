package com.sandro.part3.trace.template.code;

import org.slf4j.Logger;

@FunctionalInterface
public interface Template {

    Logger log = org.slf4j.LoggerFactory.getLogger(AbstractTemplate.class);
    default void execute() {
        long startTime = System.currentTimeMillis();
        call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    void call();
}

package com.example.sample.logging;

import com.example.sample.SampleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CRUDEntityListener {

    private static final Logger logger = LoggerFactory.getLogger(CRUDEntityListener.class);

    @PostPersist
    public void postPersist(Object entity) {
        log(Pattern.CREATE, entity);
    }

    @PostLoad
    public void postLoad(Object entity) {
        log(Pattern.READ, entity);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        log(Pattern.UPDATE, entity);
    }


    @PostRemove
    public void postRemove(Object entity) {
        log(Pattern.DELETE, entity);
    }

    void log(Pattern pattern, Object entity) {
        List<String> callTree = Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(x -> x.getClassName().startsWith(SampleApplication.class.getPackage().getName()))
                .map(x -> x.getClassName() + "." + x.getMethodName())
                .skip(2) // skip call tree due to this class
                .peek(logger::debug)
                .collect(Collectors.toList());
        logger.info("{} {} called by {}", pattern, entity.getClass().getSimpleName(), callTree);
    }

    enum Pattern {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }
}

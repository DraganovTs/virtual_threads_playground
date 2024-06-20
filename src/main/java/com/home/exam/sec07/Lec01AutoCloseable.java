package com.home.exam.sec07;

import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

public class Lec01AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(Lec01AutoCloseable.class);

    public static void main(String[] args) {

//        var executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(Lec01AutoCloseable::task);
//        log.info("submitted");
//        executorService.shutdown();

        try (var executorService = Executors.newSingleThreadExecutor()) {
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            executorService.submit(Lec01AutoCloseable::task);
            log.info("submitted");
        }

    }

    private static void task() {
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("task executed");
    }
}

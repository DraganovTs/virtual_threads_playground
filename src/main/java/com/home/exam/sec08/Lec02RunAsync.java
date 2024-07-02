package com.home.exam.sec08;

import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Lec02RunAsync {

    private static final Logger log = LoggerFactory.getLogger(Lec02RunAsync.class);

    public static void main(String[] args) {

        log.info("main start");

        runAsync()
                .thenRun(() -> log.info("it is done"))
                .exceptionally(ex -> {
                    log.info("error - {}", ex.getMessage());
                    return null;
                });

        log.info("main end");

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync() {
        log.info("method start");

        var cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            // log.info("task completed");
            throw new RuntimeException("oops");
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method end");
        return cf;
    }
}

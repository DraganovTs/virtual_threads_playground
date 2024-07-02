package com.home.exam.sec08;

import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Lec03SupplyAsync {

    private static final Logger log = LoggerFactory.getLogger(Lec03SupplyAsync.class);

    public static void main(String[] args) {
        log.info("main start");

        var cf = slowTask();
        cf.thenAccept(v -> log.info("value={}", v));

        log.info("main end");

        CommonUtils.sleep(Duration.ofSeconds(2));

    }

    private static CompletableFuture<String> slowTask() {
        log.info("method start");
        var cf = CompletableFuture.supplyAsync(()->{
           CommonUtils.sleep(Duration.ofSeconds(1));
           return "hi";
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method end");
        return cf;

    }
}

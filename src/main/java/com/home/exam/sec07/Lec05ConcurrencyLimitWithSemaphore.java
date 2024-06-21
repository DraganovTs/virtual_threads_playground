package com.home.exam.sec07;

import com.home.exam.sec07.concurrencyLimit.ConcurrencyLimiter;
import com.home.exam.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;


public class Lec05ConcurrencyLimitWithSemaphore {

    private static final Logger log = LoggerFactory.getLogger(Lec05ConcurrencyLimitWithSemaphore.class);


    public static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("tze",1).factory();
        var limiter = new ConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory),3);
        execute(limiter,20);
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter, int taskCount) throws Exception {
        try (concurrencyLimiter) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> printProductInfo(j));
            }
                log.info("submitted");
        }

    }

    private static String printProductInfo(int id) throws IOException {
        var product = Client.getProduct(id);
        log.info("{} -> {}", id, product);
        return product;
    }
}

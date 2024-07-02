package com.home.exam.sec08;

import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Lec08ThenCombine {

    private static final Logger log = LoggerFactory.getLogger(Lec07AnyOf.class);

    record Airfare(String airLine, int amount) {
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        try {
            var cf1 = getDeltaAirfare(executor);
            var cf2 = getFrontierAirfare(executor);
            var bestDeal = cf1.thenCombine(cf2, (a, b) -> a.amount() <= b.amount() ? a : b)
                    .thenApply(af -> new Airfare(af.airLine(), (int) (af.amount() * 0.9)))
                    .join();

            log.info("best deal={}", bestDeal);
        } finally {
            executor.shutdown();
        }
    }

    private static CompletableFuture<Airfare> getDeltaAirfare(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(1, 1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            log.info("Delta-{}" ,random);
            return new Airfare("Delta", random);
        }, executor);
    }

    private static CompletableFuture<Airfare> getFrontierAirfare(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            log.info("Frontier-{}" ,random);
            return new Airfare("Frontier", random);
        }, executor);
    }
}

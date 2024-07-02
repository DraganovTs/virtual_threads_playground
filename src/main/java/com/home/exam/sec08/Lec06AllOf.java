package com.home.exam.sec08;



import com.home.exam.sec08.aggregator.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Lec06AllOf {

    private static final Logger log = LoggerFactory.getLogger(Lec06AllOf.class);


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> CompletableFuture.supplyAsync(() -> aggregator.getProductDTO(id),
                        executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream().map(CompletableFuture::join)
                .toList();

        log.info("list: {}", list);
    }
}

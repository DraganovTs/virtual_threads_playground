package com.home.exam.sec07;

import com.home.exam.sec07.aggregator.AggregatorService;
import com.home.exam.sec07.aggregator.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Lec04AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec04AggregatorDemo.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> executor.submit(() -> aggregator.getProductDTO(id)))
                .toList();

        var list = futures.stream().map(Lec04AggregatorDemo::toProductDTO)
                .toList();

        log.info("list: {}", list);
    }


    private static ProductDTO toProductDTO(Future<ProductDTO> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

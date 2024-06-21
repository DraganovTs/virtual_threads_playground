package com.home.exam.sec07;

import com.home.exam.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Lec03AccessResponseUsingFuture {

    private static final Logger log = LoggerFactory.getLogger(Lec03AccessResponseUsingFuture.class);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            var product1 = executor.submit(() -> Client.getProduct(1));
            var product2 = executor.submit(() -> Client.getProduct(2));
            var product3 = executor.submit(() -> Client.getProduct(3));
            log.info("product-1: {}", product1.get());
            log.info("product-1: {}", product2.get());
            log.info("product-1: {}", product3.get());
        }



    }
}

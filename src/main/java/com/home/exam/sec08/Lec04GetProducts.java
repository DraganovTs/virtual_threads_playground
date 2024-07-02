package com.home.exam.sec08;


import com.home.exam.sec08.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Lec04GetProducts {

    private static final Logger log = LoggerFactory.getLogger(Lec04GetProducts.class);


    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var product1 = CompletableFuture.supplyAsync(() -> getProduct(1), executor);
            var product2 = CompletableFuture.supplyAsync(() -> getProduct(2), executor);
            var product3 = CompletableFuture.supplyAsync(() -> getProduct(3), executor);

            log.info("product-1: {}", product1.get());
            log.info("product-2: {}", product2.get());
            log.info("product-3: {}", product3.get());
        }
    }

    private static String getProduct(int productId) {
        try {
            return Client.getProduct(productId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
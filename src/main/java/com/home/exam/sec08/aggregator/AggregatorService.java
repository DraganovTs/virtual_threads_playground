package com.home.exam.sec08.aggregator;

import com.home.exam.sec07.externalservice.Client;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDTO getProductDTO(int id)  {
        var product = CompletableFuture.supplyAsync(() -> getProduct(id), executorService)
                .exceptionally(ex-> null)
                .orTimeout(1250, TimeUnit.MILLISECONDS)
                .exceptionally(ex-> null);;
        var rating = CompletableFuture.supplyAsync(() -> getRating(id), executorService)
                .exceptionally(ex -> -1)
                .orTimeout(1750, TimeUnit.MILLISECONDS)
                .exceptionally(ex-> -2);
        return new ProductDTO(id, product.join(), rating.join());
    }

    private String getProduct(int id) {
        try {
            return Client.getProduct(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRating(int id) {
        try {
            return Client.getRating(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
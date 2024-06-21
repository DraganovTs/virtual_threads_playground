package com.home.exam.sec07.aggregator;

import com.home.exam.sec07.externalservice.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDTO getProductDTO (int id) throws ExecutionException, InterruptedException {
        var product = executorService.submit(()-> Client.getProduct(id));
        var rating = executorService.submit(()-> Client.getRating(id));
        return new ProductDTO(id, product.get(), rating.get());
    }
}

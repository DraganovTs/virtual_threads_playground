package com.home.exam.sec07;

import com.home.exam.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lec05ConcurrencyLimit {

    private static final Logger log = LoggerFactory.getLogger(Lec05ConcurrencyLimit.class);


    public static void main(String[] args) {
        var factory = Thread.ofVirtual().name("tze",1).factory();
        execute(Executors.newFixedThreadPool(3,factory),20);
    }

    private static void execute(ExecutorService executorService, int taskCount) {
        try(executorService){
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                executorService.submit(() -> {
                    try {
                        printProductInfo(j);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            log.info("submitted");
        }
    }

    private static void printProductInfo(int id) throws IOException {
        log.info("{} -> {}",id, Client.getProduct(id));
    }
}

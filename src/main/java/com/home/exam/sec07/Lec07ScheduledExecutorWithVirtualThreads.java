package com.home.exam.sec07;

import com.home.exam.sec07.externalservice.Client;
import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lec07ScheduledExecutorWithVirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lec07ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) {
        scheduled();

    }

    // To schedule tasks periodically
    private static void scheduled() {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduler;executor){
            scheduler.scheduleAtFixedRate(() -> {
              executor.submit(()-> {
                  try {
                      printProductInfo(1);
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              });
            }, 0, 3, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id) throws IOException {
        log.info("{} -> {}",id, Client.getProduct(id));
    }
}


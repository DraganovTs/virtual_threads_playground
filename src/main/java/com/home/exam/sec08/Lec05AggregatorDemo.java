package com.home.exam.sec08;


import com.home.exam.sec08.aggregator.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lec05AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec05AggregatorDemo.class);

    public static void main(String[] args) throws Exception {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("tze",1).factory());
        var aggregator = new AggregatorService(executor);

        log.info("product={}",aggregator.getProductDTO(51));
    }
}

package com.home.exam.sec03;


import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    private static final int TASK_COUNT =  Runtime.getRuntime().availableProcessors();
    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);

    public static void main(String[] args) {
        log.info("Task count: {}", TASK_COUNT);

        for (int i = 0; i < 3; i++) {
            var totalTimeTaken = CommonUtils.timer(()-> demo(Thread.ofVirtual()));
            log.info("Total time taken whit virtual: {}ms", totalTimeTaken);
            totalTimeTaken = CommonUtils.timer(()-> demo(Thread.ofPlatform()));
            log.info("Total time taken whit platform : {}ms", totalTimeTaken);
        }
    }


    private static void demo(Thread.Builder builder){
        var latch = new CountDownLatch(TASK_COUNT);
        for (int i = 0; i <TASK_COUNT ; i++) {
            builder.start(()->{
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

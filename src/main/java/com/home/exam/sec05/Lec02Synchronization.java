package com.home.exam.sec05;

import com.home.exam.sec04.CooperativeSchedulingDemo;
import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Lec02Synchronization {
    private static final Logger log = LoggerFactory.getLogger(Lec02Synchronization.class);

    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofPlatform());

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("list size: {}", list.size());
    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}",Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Task ended. {}",Thread.currentThread());

            });
        }
    }

    private static synchronized void inMemoryTask() {
        list.add(1);
    }
}

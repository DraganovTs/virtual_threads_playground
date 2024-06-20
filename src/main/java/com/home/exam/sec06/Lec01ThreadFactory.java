package com.home.exam.sec06;

import com.home.exam.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class Lec01ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(Lec01ThreadFactory.class);

    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("tze",1).factory());

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void demo(ThreadFactory factory) {
        for (int i = 0; i < 30; i++) {

            var t = factory.newThread(() -> {
                log.info("Task started. {}", Thread.currentThread());
                var ct = factory.newThread(()-> {
                    log.info("Child task started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Child task ended. {}", Thread.currentThread());

                });
                ct.start();
                log.info("Task ended. {}", Thread.currentThread());

            });
            t.start();
        }
    }
}

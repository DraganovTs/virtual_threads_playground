package com.home.exam.sec02;

import com.home.exam.util.CommonUtils;

import java.time.Duration;

public class StackTraceDemo {

    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("virual",1));

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i <=20; i++) {
            int j = i;
            builder.start( () -> Task.execute(j));
        }
    }
}

package com.fuckSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    static int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

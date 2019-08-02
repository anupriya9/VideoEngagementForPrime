package com.amazon.prime.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.amazon.prime.hackathon"})
public class VideoEngagementForPrime {
    public static void main(String[] args) {
        SpringApplication.run(VideoEngagementForPrime.class,args);

    }
}

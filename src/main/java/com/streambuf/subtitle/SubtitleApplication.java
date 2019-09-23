package com.streambuf.subtitle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubtitleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubtitleApplication.class, args);
    }

}

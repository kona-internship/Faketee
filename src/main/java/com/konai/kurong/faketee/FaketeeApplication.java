package com.konai.kurong.faketee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaketeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaketeeApplication.class, args);
        System.out.println("test log");
        System.out.println("test log2");
    }
}

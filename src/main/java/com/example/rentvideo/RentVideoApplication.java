package com.example.rentvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.rentvideo")
@EntityScan(basePackages = "com.example.rentvideo")
@EnableJpaRepositories(basePackages = "com.example.rentvideo")
public class RentVideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentVideoApplication.class, args);
    }
}

package com.example.tink_2_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories
public class Tink2ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(Tink2ProjectApplication.class, args);
    }
}

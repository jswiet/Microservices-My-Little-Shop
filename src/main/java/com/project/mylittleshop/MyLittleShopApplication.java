package com.project.mylittleshop;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableRabbit
public class MyLittleShopApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MyLittleShopApplication.class, args);
    }
    
}

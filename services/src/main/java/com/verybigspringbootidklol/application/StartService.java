package com.verybigspringbootidklol.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.verybigspringbootidklol.services")
public class StartService {
    public static void main(String[] args) {
        SpringApplication.run(StartService.class, args);
    }
}

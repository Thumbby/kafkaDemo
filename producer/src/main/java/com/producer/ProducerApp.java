package com.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-23 13:52
 **/
@SpringBootApplication
public class ProducerApp {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(ProducerApp.class, args);
    }
}

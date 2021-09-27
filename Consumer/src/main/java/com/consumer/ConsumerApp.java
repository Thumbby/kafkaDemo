package com.consumer;

import com.consumer.listener.ConsumerListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-23 14:36
 **/
@MapperScan("com.consumer.mysql")
@SpringBootApplication
public class ConsumerApp {

    @Autowired
    private ConsumerListener consumerListener;

    public static void main(String[] args) throws Exception{
        SpringApplication.run(ConsumerApp.class, args);
    }
}

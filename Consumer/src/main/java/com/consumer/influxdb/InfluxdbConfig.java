package com.consumer.influxdb;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Thumbby
 * @description: influxDB配置
 * @date: 2021-09-26 09:49
 **/
@Configuration
@Data
public class InfluxdbConfig {

    @Value("${spring.influx.url}")
    private String influxDBUrl;

    @Value("${spring.influx.username}")
    private String username;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.database}")
    private String database;

}

package com.producer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-23 14:00
 **/
@Slf4j
@RestController
@RequestMapping(value = "producer")
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    public String sendMessage(@RequestBody JSONObject jsonObject){
        JSONArray message = jsonObject.getJSONArray("message");
        kafkaTemplate.send(topic, JSON.toJSONString(message));
        return "success";
    }
}

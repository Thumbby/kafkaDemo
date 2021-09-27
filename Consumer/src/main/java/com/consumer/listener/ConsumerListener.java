package com.consumer.listener;


import com.alibaba.fastjson.JSON;
import com.consumer.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-23 14:36
 **/
@Slf4j
@Component
public class ConsumerListener {

    @Autowired
    private ConsumerService consumerService;

    @KafkaListener(topics = {"test"})
    public void consumer(String message){
        try{
            List<Student> students = JSON.parseArray(message,Student.class);
            for(Student student:students){
                System.out.format("学生id:%d 姓名:%s 专业:%s\n",student.getId(), student.getName(),
                        student.getMajor());
            }
            consumerService.batchWrite(students);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

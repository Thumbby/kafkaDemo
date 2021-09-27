package com.consumer;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.influxdb.annotation.Column;

/**
 * @author: Thumbby
 * @description: 实体类
 * @date: 2021-09-26 10:10
 **/
@Data
@TableName(value = "student")
public class Student {

    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private int gender;

    @Column(name = "major")
    private String major;
}

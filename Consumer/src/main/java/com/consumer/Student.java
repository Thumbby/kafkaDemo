package com.consumer;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.influxdb.annotation.Column;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: Thumbby
 * @description: 实体类
 * @date: 2021-09-26 10:10
 **/
@Data
@TableName(value = "student")
public class Student {

    @TableId(type = IdType.AUTO)
    @TableField(select = false)
    private int record_id;

    @TableField(fill = FieldFill.INSERT)
    private Date record_create_time;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date record_update_time;

    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private int gender;

    @Column(name = "major")
    private String major;
}

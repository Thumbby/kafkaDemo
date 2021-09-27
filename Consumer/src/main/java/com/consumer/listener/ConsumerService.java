package com.consumer.listener;

import com.consumer.Student;
import com.consumer.influxdb.InfluxdbConfig;
import com.consumer.influxdb.InfluxdbUtils;
import com.consumer.mysql.StudentMapper;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Thumbby
 * @description: Consumer服务类
 * @date: 2021-09-26 15:52
 **/
@Service
public class ConsumerService {

    @Autowired
    private InfluxdbConfig influxdbConfig;

    @Autowired
    private StudentMapper studentMapper;

    public void write(Student student){
        InfluxdbUtils influxdbUtils = new InfluxdbUtils(influxdbConfig.getUsername(), influxdbConfig.getPassword(),
                influxdbConfig.getInfluxDBUrl(), influxdbConfig.getDatabase(), "");
        Map<String, String> tags = new HashMap<>();
        tags.put("id",Integer.toString(student.getId()));
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", student.getName());
        fields.put("gender", student.getGender());
        fields.put("major", student.getMajor());
        influxdbUtils.insert("students", tags, fields);
    }

    public void batchWrite(List<Student> students){
        InfluxdbUtils influxdbUtils = new InfluxdbUtils(influxdbConfig.getUsername(), influxdbConfig.getPassword(),
                influxdbConfig.getInfluxDBUrl(), influxdbConfig.getDatabase(), "");
        List<String> records = new ArrayList<>();
        for(Student student:students){
            studentMapper.insert(student);
            Map<String, String> tags = new HashMap<>();
            tags.put("id",Integer.toString(student.getId()));
            Map<String, Object> fields = new HashMap<>();
            fields.put("name", student.getName());
            fields.put("gender", student.getGender());
            fields.put("major", student.getMajor());
            Point point = influxdbUtils.pointBuilder("students", System.currentTimeMillis(),
                    tags, fields);
            BatchPoints batchPoints = BatchPoints.database(influxdbConfig.getDatabase()).tag("id", Integer.toString(student.getId()))
                    .retentionPolicy("").consistency(InfluxDB.ConsistencyLevel.ALL).build();
            batchPoints.point(point);
            records.add(batchPoints.lineProtocol());
        }
        influxdbUtils.batchInsert(influxdbConfig.getDatabase(), "", InfluxDB.ConsistencyLevel.ALL, records);
    }
}

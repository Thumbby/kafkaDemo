package com.consumer.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Thumbby
 * @description: influxDB工具类
 * @date: 2021-09-26 09:44
 **/
public class InfluxdbUtils {

    //用户名
    private String username;
    //密码
    private String password;
    //链接地址
    private String url;
    //数据库
    private String database;
    //保留策略
    private String retentionPolicy;

    private InfluxDB influxDB;

    public InfluxdbUtils(String username, String password, String url, String database, String retentionPolicy){
        this.username = username;
        this.password = password;
        this.url = url;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ?
                "autogen" : retentionPolicy;
        this.influxDB = influxDbBuild();
    }

    /**
     * 连接influxdb，若不存在则创建
     */
    public InfluxDB influxDbBuild(){
        if(influxDB == null){
            influxDB = InfluxDBFactory.connect(url,username,password);
        }
        try{
            if(!influxDB.databaseExists(database)){
                influxDB.createDatabase(database);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            influxDB.setRetentionPolicy(retentionPolicy);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
        return influxDB;
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public boolean ping(){
        boolean isConnected = false;
        Pong pong;
        try{
            pong = influxDB.ping();
            if(pong != null){
                isConnected = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * 设置保存策略
     * @param policyName 策略名
     * @param duration 保存天数
     * @param replication 保存副本数量
     * @param isDefault 是否设置为默认保留策略
     */
    public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault){
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ",
                database, duration, replication);
        if(isDefault) {
            sql = sql + "DEFAULT";
        }
        this.query(sql);
    }

    /**
     * 查询
     * @param command 查询语句
     */
    public QueryResult query(String command){
        return influxDB.query(new Query(command, database));
    }

    /**
     * 插入
     * @param measurement 表
     * @param tags 标签
     * @param fields 字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object>fields){
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        builder.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        influxDB.write(database, retentionPolicy, builder.build());
    }

    /**
     * 批量写入数据
     * @param database 数据库
     * @param retentionPolicy 保存策略
     * @param consistency 一致性
     * @param records 要保存的数据(调用BatchPoints.lineProtocol()可得到一条record)
     */
    public void batchInsert(final String database, final String retentionPolicy, final InfluxDB.ConsistencyLevel consistency,
                            final List<String> records){
        influxDB.write(database, retentionPolicy, consistency, records);
    }

    /**
     * 批量写入测点
     */
    public void batchInsert(BatchPoints batchPoints){
        influxDB.write(batchPoints);
    }

    /**
     * 构建Point
     * @param measurement
     * @param time
     * @param tags
     * @param fields
     */
    public Point pointBuilder(String measurement, long time, Map<String, String> tags,
                              Map<String, Object>fields){
        Point point = Point.measurement(measurement).time(time, TimeUnit.MILLISECONDS)
                .tag(tags).fields(fields).build();
        return point;
    }

    /**
     * 关闭数据库
     */
    public void close(){
        influxDB.close();
    }
}

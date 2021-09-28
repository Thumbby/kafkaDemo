package com.consumer.mysql;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-28 09:53
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("record_create_time", new Date(), metaObject);
        this.setFieldValByName("record_update_time", new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("record_update_time", new Date(), metaObject);

    }
}

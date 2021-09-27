package com.consumer.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.consumer.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-09-27 14:52
 **/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}

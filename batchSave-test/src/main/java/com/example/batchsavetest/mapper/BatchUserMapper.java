package com.example.batchsavetest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.batchsavetest.entity.BatchUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : kxhan
 * @create 2023/8/30 15:26
 */
@Mapper
public interface BatchUserMapper extends BaseMapper<BatchUser> {

    @Insert("<script>" +
            "insert into batch_user (name, age, addr, birthday) values " +
            "<foreach collection='list' item='item' separator=','> " +
            "(#{item.name}, #{item.age},#{item.addr}, #{item.birthday}) " +
            "</foreach> " +
            "</script>")
    void save(@Param("list") List<BatchUser> list);
}

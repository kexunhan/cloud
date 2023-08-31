package com.example.batchsavetest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName(value = "batch_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchUser {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private Date birthday;
  private String name;
  private Integer age;
  private String addr;


}

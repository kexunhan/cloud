package com.example.batchsavetest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.batchsavetest.entity.BatchUser;
import com.example.batchsavetest.mapper.BatchUserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author : kxhan
 * @create 2023/8/30 15:28
 */
@Service
public class BatchUserImpl extends ServiceImpl<BatchUserMapper, BatchUser> implements BatchUserService{
}

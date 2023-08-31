package com.example.batchsavetest;

import cn.hutool.core.collection.CollectionUtil;
import com.example.batchsavetest.entity.BatchUser;
import com.example.batchsavetest.mapper.BatchUserMapper;
import com.example.batchsavetest.service.BatchUserImpl;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@Data
@MapperScan("com.example.batchsavetest.*")
public class BatchSaveTestApplicationTests {

    @Autowired
    private BatchUserMapper batchUserMapper;
    @Autowired
    private BatchUserImpl batchUserImpl;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private PlatformTransactionManager transactionManager;


    /**
     * 20385
     */

    @Test
    void contextLoads() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            BatchUser build = BatchUser.builder().addr("陕西".concat(String.valueOf(i))).age(random.nextInt(100)).name(UUID.randomUUID().toString().substring(0, 8)).birthday(new Date()).build();
            batchUserMapper.insert(build);
        }
        long l = System.currentTimeMillis() - startTime;
        System.out.println(l);
    }

    /**
     * 5350
     */
    @Test
    void saveBatch() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        List list = new ArrayList();
        for (int i = 0; i < 100000; i++) {
            BatchUser build = BatchUser.builder().addr("陕西".concat(String.valueOf(i))).age(random.nextInt(100)).name(UUID.randomUUID().toString().substring(0, 8)).birthday(new Date()).build();
            list.add(build);
        }
        batchUserImpl.saveBatch(list);
        long l = System.currentTimeMillis() - startTime;
        System.out.println(l);
    }


    /**
     * 4712/4101/3970/4026
     */
    @Test
    void save() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        List list = new ArrayList();
        for (int i = 0; i < 100000; i++) {
            BatchUser build = BatchUser.builder().addr("陕西".concat(String.valueOf(i))).age(random.nextInt(100)).name(UUID.randomUUID().toString().substring(0, 8)).birthday(new Date()).build();
            list.add(build);
        }
        batchUserMapper.save(list);
        long l = System.currentTimeMillis() - startTime;
        System.out.println(l);
    }


    /**
     * batchUserMapper.save(e); 3682
     * batchUserImpl.saveBatch(e) 3482
     */
    @Test
    void save1() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        List<BatchUser> list = new ArrayList();
        for (int i = 0; i < 100000; i++) {
            BatchUser build = BatchUser.builder().addr("陕西".concat(String.valueOf(i))).age(random.nextInt(100)).name(UUID.randomUUID().toString().substring(0, 8)).birthday(new Date()).build();
            list.add(build);
        }
        List<List<BatchUser>> split = CollectionUtil.split(list, 5000);
        CountDownLatch countDownLatch = new CountDownLatch(split.size());
        split.forEach(e -> {
            threadPoolTaskExecutor.execute(() -> {
                TransactionStatus transaction = null;
                try {
                    DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
                    transaction = transactionManager.getTransaction(defaultTransactionDefinition);
                    batchUserMapper.save(e);
//                    batchUserImpl.saveBatch(e);
                    transactionManager.commit(transaction);
                } catch (Exception exception) {
                    transactionManager.rollback(transaction);
                    exception.fillInStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long l = System.currentTimeMillis() - startTime;
        System.out.println(l);
    }
}

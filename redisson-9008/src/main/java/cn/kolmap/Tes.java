package cn.kolmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author kxhan
 * @version 1.0
 * @date 2023/4/12 10:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Tes {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 布隆过滤器测试
     */
    @Test
    public void test() {
        RBloomFilter rBloomFilter = redissonClient.getBloomFilter("sqlId");
        // 初始化预期插入的数据量为10000和期望误差率为0.01
        rBloomFilter.tryInit(10000, 0.01);
        // 插入部分数据
        rBloomFilter.add("100");
        rBloomFilter.add("200");
        rBloomFilter.add("300");
        //设置过期时间
        rBloomFilter.expire(30, TimeUnit.SECONDS);
        // 判断是否存在
        System.out.println(rBloomFilter.contains("300"));
        System.out.println(rBloomFilter.contains("200"));
        System.out.println(rBloomFilter.contains("999"));
    }

    /**
     * 测试分布式Id
     */
    @Test
    public void test2() {
        final String lockKey = "aaaa";
        //通过redis的自增获取序号
        RAtomicLong atomicLong = redissonClient.getAtomicLong(lockKey);
        //设置过期时间
        atomicLong.expire(30, TimeUnit.SECONDS);
        // 获取值
        System.out.println(atomicLong.incrementAndGet());
    }


    /**
     * 测试分布式锁
     */
    @Test
    public void test3() {
        //获取锁对象实例
        final String lockKey = "abc";
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            //尝试5秒内获取锁，如果获取到了，最长60秒自动释放
            boolean res = rLock.tryLock(5L, 60L, TimeUnit.SECONDS);
            if (res) {
                //成功获得锁，在这里处理业务
                System.out.println("获取锁成功");
            }
        } catch (Exception e) {
            System.out.println("获取锁失败，失败原因：" + e.getMessage());
        } finally {
            //无论如何, 最后都要解锁
            rLock.unlock();
        }

    }
}

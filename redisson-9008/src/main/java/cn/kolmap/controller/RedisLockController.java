package cn.kolmap.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("")
public class RedisLockController {
    private static String product1Count = "product1Count";//商品1的数量key
    private static String lockKey = "lock";//分布式锁的key

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired    
      private RedissonClient redisson;


    /**
     * 初始化设置商品数量
     *
     * @return
     */
    @RequestMapping("/setCount")
    public String setValue() {
        redisTemplate.opsForValue().set(product1Count, "100");
        return "success";
    }

    /**
     * 模拟秒杀抢购，并发多个请求过来，查看是否出现超卖
     *
     * @return
     */
    @RequestMapping("/spike")
    public String spike() {
        String flag = "success";
        RLock lock = redisson.getLock(lockKey);
        try {
            //lock.lockAsync(5 , TimeUnit.SECONDS);
            //lock.lock(5, TimeUnit.SECONDS); //设置60秒自动释放锁  （默认是30秒自动过期）
            Future<Boolean> res = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
            boolean result = res.get();
            System.out.println("result:" + result);
            if (result) {
                int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count).toString());
                if (stock > 0) {
                    redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
                } else {
                    flag = "fail";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //释放锁
        }
        return flag;
    }
}
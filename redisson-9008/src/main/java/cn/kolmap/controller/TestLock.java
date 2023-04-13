package cn.kolmap.controller;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author kxhan
 * @version 1.0
 * @date 2023/4/13 15:10
 * <p>
 * 分布式锁中的王者方案 - Redisson
 * 参考：https://mp.weixin.qq.com/s/CbnPRfvq4m1sqo2uKI6qQw
 * https://mp.weixin.qq.com/s?__biz=MzAwMjI0ODk0NA==&mid=2451954663&idx=1&sn=4bd071b6aaede114263f88c790b61371&chksm=8d1c2278ba6bab6eca2ef44f21b2178cc719fffe124289b68128c0dad72429fe5f286854157a&scene=21#wechat_redirect
 *
 *
 * 读锁 + 读锁：相当于没加锁，可以并发读。
 * 读锁 + 写锁：写锁需要等待读锁释放锁。
 * 写锁 + 写锁：互斥，需要等待对方的锁释放。
 * 写锁 + 读锁：读锁需要等待写锁释放。
 *
 *
 *
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestLock {

    private final RedissonClient redissonClient;


    @GetMapping("test-lock")
    public String TestLock() {
        // 1.获取锁，只要锁的名字一样，获取到的锁就是同一把锁。
        RLock lock = redissonClient.getLock("WuKong-lock");

        // 2.加锁
        lock.lock();
        try {
            System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            Thread.sleep(10000);
        } catch (Exception e) {
            //TODO
        } finally {
            lock.unlock();
            // 3.解锁
            System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
        }

        return "test lock ok";
    }


    /**
     * 停车，占用停车位
     * 总共 3 个车位  park 会减
     */
    @RequestMapping("park")
    public String park() throws InterruptedException {
        // 获取信号量（停车场）
        RSemaphore park = redissonClient.getSemaphore("park");
        // 获取一个信号（停车位）
        park.acquire();

        return "OK";
    }

    /**
     * 释放车位
     * 总共 3 个车位   park 会加
     */
    @RequestMapping("leave")
    public String leave() throws InterruptedException {
        // 获取信号量（停车场）
        RSemaphore park = redissonClient.getSemaphore("park");
        // 释放一个信号（停车位）
        park.release();

        return "OK";
    }


    /**
     * 读写锁
     */
    @RequestMapping("read1")
    public String readLock1() throws InterruptedException {
        RLock rLock = null;
        try {
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("kx-readWrite");
            rLock = readWriteLock.readLock();
            rLock.lock();
            System.out.println("read1 ok");
        } finally {
            if (Objects.nonNull(rLock)) {
                Thread.sleep(10000);
                rLock.unlock();
            }
        }
        return null;
    }

    /**
     * 读写锁
     */
    @RequestMapping("read2")
    public String readLock2() throws InterruptedException {
        RLock rLock = null;
        try {
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("kx-readWrite");
            rLock = readWriteLock.readLock();
            rLock.lock();
            System.out.println("read2 OK");

        } finally {
            if (Objects.nonNull(rLock)) {
                Thread.sleep(10000);
                rLock.unlock();
            }
        }
        return null;
    }

    /**
     * 读写锁
     */
    @RequestMapping("write1")
    public String writeLock1() throws InterruptedException {
        RLock rLock = null;
        try {
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("kx-readWrite");
            rLock = readWriteLock.writeLock();
            rLock.lock();
            System.out.println("write1 ok");
        } finally {
            if (Objects.nonNull(rLock)) {
                Thread.sleep(10000);
                rLock.unlock();
            }
        }
        return null;
    }

    /**
     * 读写锁
     */
    @RequestMapping("write2")
    public String writeLock2() throws InterruptedException {
        RLock rLock = null;
        try {
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("kx-readWrite");
            rLock = readWriteLock.writeLock();
            rLock.lock();
            System.out.println("write2 ok");
        } finally {
            if (Objects.nonNull(rLock)) {
                Thread.sleep(10000);
                rLock.unlock();
            }
        }
        return null;
    }
}

package cn.kolmap;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kxhan
 * @version 1.0
 * @date 2023/4/7 17:55
 */
@SpringBootApplication
public class Redisson9008Application {



    public static void main(String[] args) {
        SpringApplication.run(Redisson9008Application.class, args);

    }



}

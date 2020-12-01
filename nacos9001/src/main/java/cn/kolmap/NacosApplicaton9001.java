package cn.kolmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author kxhan
 * @createTime 2020/11/10
 */

@SpringBootApplication
@EnableDiscoveryClient
public class NacosApplicaton9001 {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplicaton9001.class, args);
    }
}



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
public class NacosApplicaton9002 {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplicaton9002.class, args);
    }
}

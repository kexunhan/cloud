package cn.kolmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author kxhan
 * @createTime 2020/12/01
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConfigClinet3377 {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClinet3377.class, args);
    }
}

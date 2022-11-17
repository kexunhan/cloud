package cn.kolmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 * 1. 路由:构建网关的基本模块ID，目标URL，一系列的断言和过滤器组成，如果断言为true，则匹配该路由
 * 2. 断言：参考Java8的Perdicate类
 * 3. 过滤：gatewayFilter的实例，使用过滤器可以在路由前后进行修改
 *
 * gateway 网关不需要引入spring-boot-start-web 的pom
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}

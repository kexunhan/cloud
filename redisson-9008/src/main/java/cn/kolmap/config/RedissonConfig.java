package cn.kolmap.config;

import cn.kolmap.properties.RedisConfigProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kxhan
 * @version 1.0
 * @date 2023/4/12 11:02
 */
@Configuration
public class RedissonConfig {

    private final RedisConfigProperties redisConfigProperties;

    public RedissonConfig(RedisConfigProperties redisConfigProperties) {
        this.redisConfigProperties = redisConfigProperties;
    }

    @Bean
    public RedissonClient getClient(){

        // redisson 3.5版本后集群地址必须以：redis:// 或 rediss://

        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress(redisConfigProperties.getMasterAddress()).setPassword(redisConfigProperties.getPassword()).setSlaveAddresses(redisConfigProperties.getSlaveAddresses());
        return Redisson.create(config);
    }
}

package cn.kolmap.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author kxhan
 * @version 1.0
 * @date 2023/4/12 11:06
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfigProperties {


    private String password;

    private String masterAddress;

    private Set<String> slaveAddresses;

}

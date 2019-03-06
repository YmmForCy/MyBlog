package org.chenyu.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author chenyu
 * @Date 2019/3/4 23:49
 **/
public class RedisConfig {
    @Value("${redis.maxIdle}")
    public String maxIdle;
    @Value("${redis.maxWait}")
    public String maxWait;
    @Value("${redis.testOnBorrow}")
    public String testOnBorrow;

    @Value("${redis.host}")
    public String hostName;
    @Value("${redis.port}")
    public String port;
    @Value("${redis.pass}")
    public String password;
}

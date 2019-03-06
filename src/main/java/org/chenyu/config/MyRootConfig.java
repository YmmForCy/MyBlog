package org.chenyu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.chenyu.task.ArticleTask;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * @ClassName RootConfig
 * @Description TODO
 * @Author chenyu
 * @Date 2019/2/19 9:16
 **/
@Configuration
@ComponentScan(
        value = "org.chenyu",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class))
@PropertySource(value = {"classpath:/jdbc.properties", "classpath:/redis-config.properties"}, encoding = "UTF-8")
@EnableScheduling
public class MyRootConfig {
    @Bean
    public DBConfig dbConfig() {
        return new DBConfig();
    }
    @Bean
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }
    /*@Bean
    public ArticleTask articleTask() {
        return new ArticleTask();
    }*/

    //mybatis：sqlsession、datasource
    @Bean
    public BasicDataSource dataSource(DBConfig dbConfig) {
//        System.out.println("driver:"+DBConfig.driver);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbConfig.driver);
        dataSource.setUrl(dbConfig.url);
        dataSource.setUsername(dbConfig.username);
        dataSource.setPassword(dbConfig.password);
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://119.23.226.211:3306/blog?useUnicode=true&characterEncoding=UTF-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        dataSource.setInitialSize(initialSize);
//        dataSource.setMaxActive(maxActive);
//        dataSource.setMaxIdle(maxIdle);
//        dataSource.setMinIdle(minIdle);
//        dataSource.setMaxWait(maxWait);
        return dataSource;
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DBConfig dbConfig) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource(dbConfig));
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("org.chenyu.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }
    @Bean
    public DataSourceTransactionManager transactionManager(DBConfig dbConfig) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource(dbConfig));
        return dataSourceTransactionManager;
    }

    /*@Bean
    public JedisPoolConfig jedisPoolConfig(RedisConfig redisConfig) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(Long.valueOf(redisConfig.maxWait));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(redisConfig.maxIdle));
        jedisPoolConfig.setTestOnBorrow(Boolean.getBoolean(redisConfig.testOnBorrow));

        return new JedisPoolConfig();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisConfig redisConfig, JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisConfig.hostName);
        factory.setPort(Integer.valueOf(redisConfig.port));
        factory.setPassword(redisConfig.password);
        factory.setPoolConfig(jedisPoolConfig);

        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }*/
}

package com.funnytree.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description redis配置类
 * @ClassName RedisConfig
 * @author 李英夫
 * @since 2018/11/12 2:10
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Configuration
@Import(CacheProperties.class)
public class RedisConfig {

    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setEnableTransactionSupport(true);
        return template;
    }
    /**
     * redis缓存配置
     * @see RedisCacheConfiguration 默认配置看这里
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,
        // 但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
        // ClassLoader loader = this.getClass().getClassLoader();
        // JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
        // RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
        // RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        // 可以使用Jackson2JsonRedisSerialize 替换默认序列化，
        // 但是Jackson2JsonRedisSerializer反序列化时需要进行配置，我们使用GenericJackson2JsonRedisSerializer避免配置
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair); // 设置缓存有效期一小时
        //根据配置文件补充设置配置信息
        RedisCacheConfiguration resetConfig = configReset(redisCacheConfiguration);
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        // 返回初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, resetConfig);
    }

    /**
     * 根据配置文件补充设置redis缓存配置
     * @return config 重设后的配置信息
     */
    private RedisCacheConfiguration configReset(RedisCacheConfiguration config) {
        CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

}

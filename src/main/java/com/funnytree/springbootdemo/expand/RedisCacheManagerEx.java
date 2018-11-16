package com.funnytree.springbootdemo.expand;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @Description redis缓存管理类扩展
 * @ClassName RedisCacheManagerEx
 * @author 李英夫
 * @since 2018/11/16 15:35
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
public class RedisCacheManagerEx extends RedisCacheManager {

    private RedisCacheWriter cacheWriter;

    private RedisCacheConfiguration defaultCacheConfiguration;

    public RedisCacheManagerEx(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return new RedisCacheEx(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfiguration);
    }
}

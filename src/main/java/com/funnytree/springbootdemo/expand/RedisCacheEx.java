package com.funnytree.springbootdemo.expand;

import java.util.concurrent.Callable;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @Description Redis缓存类扩展
 * @ClassName RedisCacheEx
 * @author 李英夫
 * @since 2018/11/16 15:50
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
public class RedisCacheEx extends RedisCache {

    private static final byte[] REDIS_CACHE_EX_GET_LOCK = new byte[0];
    /**
     * Create new {@link RedisCacheEx}.
     *
     * @param name        must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param cacheConfig must not be {@literal null}.
     */
    RedisCacheEx(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }

    /**
     * 缓存读取方法，对父类读取缓存时细粒度控制不足的问题进行了优化，针对缓存穿透问题是一个更好的解决方案
     * @param key 缓存的key
     * @param valueLoader 缓存读取回调
     * @param <T> 返回值类型
     * @return
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper result = get(key);
        T value;
        // 双重检查锁
        if (result != null) {
            value =  (T) result.get();
        }else{
            synchronized (REDIS_CACHE_EX_GET_LOCK){
                result = get(key);
                if(result != null){
                    value =  (T) result.get();
                }else{
                    try {
                        value =  valueLoader.call();
                    } catch (Exception e) {
                        throw new ValueRetrievalException(key, valueLoader, e);
                    }
                    put(key, value);
                }
            }
        }
        return value;
    }

}

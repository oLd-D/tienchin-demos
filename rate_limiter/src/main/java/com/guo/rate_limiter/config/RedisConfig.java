package com.guo.rate_limiter.config;

import io.lettuce.core.dynamic.RedisCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfig {
    /**
     * RedisTemplate的默认序列化方案会在key和value前加一些字符串，命令行或者脚本查询的时候会出现问题，所以需要稍微定制一下
     */
    @Bean
    RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 该序列化的特点是会在序列化的对象前后加个双引号
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }

    // 将脚本注入对象
    @Bean
    DefaultRedisScript<Long> limitScript(){
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        return script;
    }
}

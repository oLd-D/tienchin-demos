package com.guo.rate_limiter.controller;

import com.guo.rate_limiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    /**
     * 限流，10秒内这个接口可以访问3次
     */
    @RateLimiter(time=30,count = 3)
    public String hello(){
        return "hello";
    }
}

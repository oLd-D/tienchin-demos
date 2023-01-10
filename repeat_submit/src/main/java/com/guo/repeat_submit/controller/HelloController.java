package com.guo.repeat_submit.controller;

import com.guo.repeat_submit.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @PostMapping("/hello")
    @RepeatSubmit(interval = 10000)
    public String hello(@RequestBody String json){
        return json;
    }
}

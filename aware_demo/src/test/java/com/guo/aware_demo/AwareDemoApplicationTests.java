package com.guo.aware_demo;

import com.guo.aware_demo.service.BeanUtils;
import com.guo.aware_demo.service.BeanUtils2;
import com.guo.aware_demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwareDemoApplicationTests {

    @Test
    void contextLoads() {
        // UserService userService = BeanUtils.getBean("userService");
        UserService userService = BeanUtils2.getBean("userService");
        userService.sayHello();
    }

}

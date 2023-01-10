package com.guo.aware_demo.service;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service
public class UserService implements BeanNameAware {

    public void sayHello() {
        System.out.println("hello guo");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean name is "+name);
    }
}

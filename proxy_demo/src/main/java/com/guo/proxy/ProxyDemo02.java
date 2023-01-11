package com.guo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 */
public class ProxyDemo02 {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        // MethodInterceptor是类, 无法使用lambda表达式
        enhancer.setCallback(new DogInterceptor());
        Dog dog = (Dog) enhancer.create();
        dog.eat();
    }
}

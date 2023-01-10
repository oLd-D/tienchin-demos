package com.guo.aware_demo.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtils implements BeanFactoryAware {
    private static BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanUtils.beanFactory = beanFactory;
    }

    public static <T> T getBean(String beanName) {
        return (T) beanFactory.getBean(beanName);
    }
}

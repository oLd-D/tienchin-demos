package com.guo.dd.service;

import com.guo.dd.annotation.DataSource;
import com.guo.dd.mapper.SlaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlaveService {
    @Autowired
    SlaveMapper slaveMapper;

    @DataSource("slave")
    public void updateUserAge(String username, Integer age){
        slaveMapper.updateUserAge(username, age);
        int i=1/0;
    }
}

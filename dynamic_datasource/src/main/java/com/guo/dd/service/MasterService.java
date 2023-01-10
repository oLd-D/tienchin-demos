package com.guo.dd.service;

import com.guo.dd.annotation.DataSource;
import com.guo.dd.mapper.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterService {
    @Autowired
    MasterMapper masterMapper;

    @DataSource("master")
    public void updateUserAge(String username, Integer age){
        masterMapper.updateUserAge(username,age);
    }
}

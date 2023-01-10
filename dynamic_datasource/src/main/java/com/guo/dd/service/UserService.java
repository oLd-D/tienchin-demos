package com.guo.dd.service;

import com.guo.dd.annotation.DataSource;
import com.guo.dd.mapper.UserMapper;
import com.guo.dd.model.User;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MasterService masterService;
    @Autowired
    SlaveService slaveService;

    // @Transactional
    @GlobalTransactional
    public void test(){
        masterService.updateUserAge("a",200);
        slaveService.updateUserAge("c",201);
    }

    @DataSource("master")
    @Transactional
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }
}

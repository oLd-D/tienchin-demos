package com.guo.dd.controller;

import com.guo.dd.datasource.DataSourceType;
import com.guo.dd.model.User;
import com.guo.dd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class DataSourceController {
    private static final Logger logger= LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    UserService userService;

    @PostMapping("/dstype")
    public void setDsType(String dsType, HttpSession httpSession){
        httpSession.setAttribute(DataSourceType.DS_SESSION_KEY,dsType);
        logger.info("数据源切换为{}",dsType);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}

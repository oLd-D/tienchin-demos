package com.guo.ds.controller;

import com.guo.ds.entity.User;
import com.guo.ds.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/")
    public List<User> getAllUsers(User user){
        return userService.getAllUsers(user);
    }
}

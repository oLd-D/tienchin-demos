package com.guo.ds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.ds.annotatino.DataScope;
import com.guo.ds.entity.User;
import com.guo.ds.mapper.UserMapper;
import com.guo.ds.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> qw = new QueryWrapper<>();
        // 将传入的username作为查询值进行返回, 一个是外部的username, 一个是User对象的username, 所以需要两个参数
        qw.lambda().eq(User::getUserName, username);
        // 执行相应的查询
        User user = getOne(qw);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        // 表中不存在roles字段, 需要通过userId根据role_user表进行查询
        user.setRoles(userMapper.getRolesByUid(user.getUserId()));
        return user;
    }


    @Override
    @DataScope(deptAlias ="d",userAlias = "u")
    public List<User> getAllUsers(User user) {
        return userMapper.getAllUsers(user);
    }
}

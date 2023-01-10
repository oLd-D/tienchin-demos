package com.guo.ds.service;

import com.guo.ds.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface IUserService extends IService<User> {

    List<User> getAllUsers(User user);
}

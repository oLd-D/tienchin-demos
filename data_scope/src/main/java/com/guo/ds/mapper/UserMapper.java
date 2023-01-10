package com.guo.ds.mapper;

import com.guo.ds.entity.Role;
import com.guo.ds.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface UserMapper extends BaseMapper<User> {

     List<Role> getRolesByUid(Long deptId);

    List<User> getAllUsers(User user);
}

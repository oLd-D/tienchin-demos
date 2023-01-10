package com.guo.ds.service.impl;

import com.guo.ds.entity.Role;
import com.guo.ds.mapper.RoleMapper;
import com.guo.ds.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles(Role role) {
        return roleMapper.getAllRoles(role);
    }
}

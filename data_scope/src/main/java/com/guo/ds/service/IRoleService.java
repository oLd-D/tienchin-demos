package com.guo.ds.service;

import com.guo.ds.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface IRoleService extends IService<Role> {

    List<Role> getAllRoles(Role role);
}

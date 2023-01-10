package com.guo.ds.mapper;

import com.guo.ds.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getAllRoles(Role role);
}

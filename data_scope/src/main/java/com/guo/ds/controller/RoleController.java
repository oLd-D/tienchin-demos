package com.guo.ds.controller;

import com.guo.ds.annotatino.DataScope;
import com.guo.ds.entity.Role;
import com.guo.ds.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/")
    @DataScope(deptAlias = "d",userAlias = "u")
    public List<Role> getAllRoles(Role role) {
        return roleService.getAllRoles(role);
    }
}

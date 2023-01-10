package com.guo.ds.controller;

import com.guo.ds.annotatino.DataScope;
import com.guo.ds.entity.Dept;
import com.guo.ds.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    IDeptService deptService;

    @GetMapping("/")
    @DataScope(deptAlias = "d")
    public List<Dept> getAllDepts(Dept dept){
        return deptService.getAllDepts(dept);
    }
}

package com.guo.ds.service;

import com.guo.ds.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface IDeptService extends IService<Dept> {

    List<Dept> getAllDepts(Dept dept);
}

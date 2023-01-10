package com.guo.ds.mapper;

import com.guo.ds.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
public interface DeptMapper extends BaseMapper<Dept> {

    List<Dept> getAllDepts(Dept dept);
}

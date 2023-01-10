package com.guo.ds.service.impl;

import com.guo.ds.annotatino.DataScope;
import com.guo.ds.entity.Dept;
import com.guo.ds.mapper.DeptMapper;
import com.guo.ds.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author guo
 * @since 2022-08-30
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Autowired
    DeptMapper deptMapper;

    @Override
    @DataScope(deptAlias = "d")
    public List<Dept> getAllDepts(Dept dept) {
        return deptMapper.getAllDepts(dept);
    }
}

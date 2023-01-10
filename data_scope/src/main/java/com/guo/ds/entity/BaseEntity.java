package com.guo.ds.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.HashMap;
import java.util.Map;

public class BaseEntity {
    @TableField(exist = false)
    private Map<String,String> params=new HashMap<>();

    public Map<String,String> getParams(){
        return params;
    }
    public void setParams(Map<String,String> params){
        this.params=params;
    }
}

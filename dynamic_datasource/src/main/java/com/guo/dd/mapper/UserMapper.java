package com.guo.dd.mapper;

import com.guo.dd.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface  UserMapper {
    @Select("select * from user")
    List<User> getAllUsers();
}

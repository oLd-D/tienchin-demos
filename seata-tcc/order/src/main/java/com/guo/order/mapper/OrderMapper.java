package com.guo.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    @Insert("insert into order_tbl(user_id,commodity_code, count, money) values(#{userId},#{productId},#{count},#{money})")
    int addOrder(@Param("userId") String userId,@Param("productId") String productId,@Param("count") Integer count,@Param("money") double money);
}

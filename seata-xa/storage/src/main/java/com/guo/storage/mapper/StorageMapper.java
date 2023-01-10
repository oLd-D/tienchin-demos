package com.guo.storage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper {
    @Update("update storage_tbl set count=count-#{count} where commodity_code=#{productId}")
    int deductStorage(@Param("productId") String productId,@Param("count") Integer count);

    @Select("select count from storage_tbl where commodity_code=#{commodity_code}")
    int getCountByCommodityCode(String commodity_code);
}

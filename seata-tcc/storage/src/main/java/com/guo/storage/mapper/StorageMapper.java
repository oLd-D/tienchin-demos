package com.guo.storage.mapper;

import com.guo.storage.model.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper {
    @Select("select * from storage_tbl where commodity_code=#{commodityCode}")
    Storage getStorageById(String productId);

    @Update("update storage_tbl set count=#{count}, freezeCount=#{freezeCount} where commodity_code=#{commodityCode}")
    Integer updateStorage(Storage storage);
}

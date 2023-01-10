package com.guo.storage.service;

import com.guo.common.RespBean;
import com.guo.storage.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    @Autowired
    StorageMapper storageMapper;

    public boolean deduct(String productId, Integer count){
        int result=storageMapper.getCountByCommodityCode(productId);
        if(result<count){
            throw new RuntimeException("库存不足, 扣库存失败");
        }
        int i = storageMapper.deductStorage(productId, count);
        return i==1;
    }
}

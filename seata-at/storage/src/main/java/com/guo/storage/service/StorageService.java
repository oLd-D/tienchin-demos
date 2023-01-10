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
        int i = storageMapper.deductStorage(productId, count);
        int result=storageMapper.getCountByCommodityCode(productId);
        if(result>=0){
            return true;
        }
        throw new RuntimeException("库存不足, 扣库存失败");
    }
}

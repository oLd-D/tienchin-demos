package com.guo.storage.service;

import com.guo.common.RespBean;
import com.guo.storage.mapper.StorageMapper;
import com.guo.storage.model.Storage;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    private static final Logger logger= LoggerFactory.getLogger(StorageService.class);

    @Autowired
    StorageMapper storageMapper;

    public boolean prepare(String productId, Integer count) {
        Storage storage=storageMapper.getStorageById(productId);
        if(storage==null){
            throw new RuntimeException("商品不存在");
        }
        if(storage.getCount()<count){
            throw new RuntimeException("库存不足, 预扣库存失败");
        }
        storage.setCount(storage.getCount()-count);
        storage.setFreezeCount(storage.getFreezeCount()+count);
        Integer i=storageMapper.updateStorage(storage);
        logger.info("{} 商品库存冻结 {} 个", productId, count);
        return i==1;
    }

    // 正式修改库存
    public boolean commit(BusinessActionContext actionContext) {
        String productId = (String) actionContext.getActionContext("productId");
        Integer count = (Integer) actionContext.getActionContext("count");
        Storage storage = storageMapper.getStorageById(productId);
        if(storage.getFreezeCount()<count){
            throw new RuntimeException("冻结的库存不足, 可能被其他线程修改了, 扣库存失败");
        }
        storage.setFreezeCount(storage.getCount()-count);
        Integer i= storageMapper.updateStorage(storage);
        logger.info("{} 商品扣库存 {} 个", productId, count);
        return i==1;
    }

    public boolean rollback(BusinessActionContext actionContext) {
        String productId = (String) actionContext.getActionContext("productId");
        Integer count = (Integer) actionContext.getActionContext("count");
        Storage storage = storageMapper.getStorageById(productId);
        if(storage.getFreezeCount()>=count){
            storage.setCount(storage.getCount()+count);
            storage.setFreezeCount(storage.getFreezeCount()-count);
            Integer i= storageMapper.updateStorage(storage);
            logger.info("{} 商品释放库存 {} 个", productId, count);
            return i==1;
        }
        logger.info("{} 商品压根就没冻结库存, 要么商品不存在, 要门库存不足");
        return true;
    }
}

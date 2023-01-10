package com.guo.storage.controller;

import com.guo.common.RespBean;
import com.guo.common.feign.StorageServiceApi;
import com.guo.storage.service.StorageService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController implements StorageServiceApi {
    @Autowired
    StorageService storageService;


    @Override
    public boolean prepare(BusinessActionContext actionContext, String productId, Integer count) {
        return storageService.prepare(productId,count);
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        return storageService.commit(actionContext);
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        return storageService.rollback(actionContext);
    }
}

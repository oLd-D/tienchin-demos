package com.guo.business.sesrvice;

import com.guo.common.feign.OrderServiceApi;
import com.guo.common.feign.StorageServiceApi;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
    @Autowired
    OrderServiceApi orderServiceApi;

    @Autowired
    StorageServiceApi storageServiceApi;

    @GlobalTransactional
    public void purchase(String account, String productId, Integer count){
        String xid = RootContext.getXID();
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        orderServiceApi.prepare(actionContext,account,productId,count);
        storageServiceApi.prepare(actionContext,productId,count);
    }
}

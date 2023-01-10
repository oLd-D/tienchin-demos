package com.guo.order.controller;

import com.guo.common.RespBean;
import com.guo.common.feign.OrderServiceApi;
import com.guo.order.service.OrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderServiceApi {
    @Autowired
    OrderService orderService;

    @Override
    public boolean prepare(BusinessActionContext actionContext, String userId, String productId, Integer count) {
        return orderService.prepare(actionContext, userId, count,productId);
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        return orderService.commit(actionContext);
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        return orderService.rollback(actionContext);
    }
}

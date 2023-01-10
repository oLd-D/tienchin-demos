package com.guo.order.service;

import com.guo.common.RespBean;
import com.guo.common.feign.AccountServiceApi;
import com.guo.order.mapper.OrderMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private static final Logger logger= LoggerFactory.getLogger(OrderService.class);

    @Autowired
    AccountServiceApi accountServiceApi;

    @Autowired
    OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean prepare(BusinessActionContext actionContext, String userId, Integer count, String productId) {
        // 添加订单之前先检查账户有没有问题
        boolean prepare = accountServiceApi.prepare(actionContext, userId, count * 100.0);
        logger.info("{} 用户购买了 {} 商品, 共计 {} 件, 预下单成功", userId, productId,count);
        return prepare;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        String productId = (String) actionContext.getActionContext("productId");
        Integer count = (Integer) actionContext.getActionContext("count");
        Integer i=orderMapper.addOrder(userId,productId,count,count*100.0);
        logger.info("{} 用户购买了 {} 商品, 共计 {} 件, 下单成功", userId, productId,count);
        return i==1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        String productId = (String) actionContext.getActionContext("productId");
        Integer count = (Integer) actionContext.getActionContext("count");
        logger.info("{} 用户购买了 {} 商品, 共计 {} 件, 订单回滚成功", userId, productId,count);
        return true;
    }
}

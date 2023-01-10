package com.guo.order.service;

import com.guo.common.RespBean;
import com.guo.order.feign.AccountFeign;
import com.guo.order.mapper.OrderMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    AccountFeign accountFeign;

    @Autowired
    OrderMapper orderMapper;

    public boolean createOrder(String account,String productId, Integer count){
        System.out.println("进入OrderService # createOrder");
        // 账户的钱先减
        RespBean respBean = accountFeign.deduct(account, count * 100.0);
        // 再创建订单
        int i=orderMapper.addOrder(account,productId,count,count*100.0);
        return i==1&&respBean.getStatus()==200;
    }
}

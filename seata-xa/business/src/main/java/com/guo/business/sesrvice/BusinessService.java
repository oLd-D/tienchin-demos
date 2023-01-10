package com.guo.business.sesrvice;

import com.guo.business.feign.OrderFeign;
import com.guo.business.feign.StorageFeign;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public class BusinessService {
    @Autowired
    OrderFeign orderFeign;

    @Autowired
    StorageFeign storageFeign;

    @GlobalTransactional
    public void purchase(String account, String productId, Integer count){
        System.out.println("执行了orderFeign.createOrder(account,productId,count)方法-------------------------------------------------------------");
        orderFeign.createOrder(account,productId,count);
        System.out.println("执行了storageFeign.deduct(productId, count)方法-------------------------------------------------------------");
        storageFeign.deduct(productId,count);
    }
}

package com.guo.order.controller;

import com.guo.common.RespBean;
import com.guo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public RespBean createOrder(String account, String productId,Integer count){
        if (orderService.createOrder(account, productId, count)) {
            System.out.println("下单成功----------------------------------------");
            return RespBean.ok("下单成功");
        }
        return RespBean.error("下单失败");
    }
}

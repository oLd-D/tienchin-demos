package com.guo.business.feign;

import com.guo.common.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("order")
public interface OrderFeign {
    @PostMapping("/createOrder")
    RespBean createOrder(@RequestParam("account") String account,@RequestParam("productId") String productId,@RequestParam("count") Integer count);
}

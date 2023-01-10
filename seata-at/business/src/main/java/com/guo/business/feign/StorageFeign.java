package com.guo.business.feign;

import com.guo.common.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("storage")
public interface StorageFeign {
    @PostMapping("/deduct")
    RespBean deduct(@RequestParam("productId") String productId,@RequestParam("count") Integer count);
}

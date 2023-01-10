package com.guo.business.feign;

import com.guo.common.feign.OrderServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("order")
public interface OrderServiceApiImpl extends OrderServiceApi {

}

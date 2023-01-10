package com.guo.order.feign;

import com.guo.common.feign.AccountServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("account")
public interface AccountServiceApiImpl extends AccountServiceApi {
}

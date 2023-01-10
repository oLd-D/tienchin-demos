package com.guo.business.feign;

import com.guo.common.feign.StorageServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("storage")
public interface StorageServiceApiImpl extends StorageServiceApi {
}

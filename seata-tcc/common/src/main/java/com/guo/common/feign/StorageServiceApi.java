package com.guo.common.feign;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@LocalTCC
public interface StorageServiceApi {
    @TwoPhaseBusinessAction(name="storageServiceApi", commitMethod = "commit", rollbackMethod = "rollback")
    @RequestMapping("/storage/deduct/prepare")
    boolean prepare(@RequestBody BusinessActionContext actionContext,
                    @RequestParam(name = "productId") @BusinessActionContextParameter(paramName = "productId") String productId,
                    @RequestParam(name="count") @BusinessActionContextParameter(paramName = "count") Integer count);

    @RequestMapping("/storage/deduct/commit")
    boolean commit(@RequestBody BusinessActionContext actionContext);

    @RequestMapping("/storage/deduct/rollback")
    boolean rollback(@RequestBody BusinessActionContext actionContext);
}

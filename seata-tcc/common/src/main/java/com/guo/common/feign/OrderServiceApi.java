package com.guo.common.feign;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@LocalTCC
public interface OrderServiceApi {
    @TwoPhaseBusinessAction(name="orderServiceApi", commitMethod = "commit", rollbackMethod = "rollback")
    @RequestMapping("/order/create/prepare")
    boolean prepare(@RequestBody BusinessActionContext actionContext,
                    @RequestParam("userId") @BusinessActionContextParameter(paramName = "userId") String userId,
                    @RequestParam("productId") @BusinessActionContextParameter(paramName = "productId") String productId,
                    @RequestParam("count") @BusinessActionContextParameter(paramName = "count") Integer count);

    @RequestMapping("/order/create/commit")
    boolean commit(@RequestBody BusinessActionContext actionContext);

    @RequestMapping("/order/create/rollback")
    boolean rollback(@RequestBody BusinessActionContext actionContext);
}

package com.guo.account.controller;

import com.guo.account.service.AccountService;
import com.guo.common.RespBean;
import com.guo.common.feign.AccountServiceApi;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountServiceApi {
    @Autowired
    AccountService accountService;

    @Override
    public boolean prepare(BusinessActionContext actionContext, String userId, Double money) {
        return accountService.prepare(userId, money);
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        return accountService.commit(actionContext);
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        return accountService.rollback(actionContext);
    }
}

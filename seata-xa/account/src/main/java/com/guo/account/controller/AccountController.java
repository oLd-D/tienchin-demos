package com.guo.account.controller;

import com.guo.account.service.AccountService;
import com.guo.common.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/deductAccount")
    public RespBean deduct(String account, Double money) {
        if (accountService.deductAccount(account, money)) {
            return RespBean.ok("扣款成功");
        }
        return RespBean.error("扣款失败");

    }
}

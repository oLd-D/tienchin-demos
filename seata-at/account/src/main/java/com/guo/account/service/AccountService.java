package com.guo.account.service;

import com.guo.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountMapper accountMapper;

    public boolean deductAccount(String account,Double money){
        accountMapper.updateAccount(account,money);
        Double m=accountMapper.getMoneyByAccount(account);
        if(m>=0){
            return true;
        }
        throw  new RuntimeException("账户余额不足");
    }
}

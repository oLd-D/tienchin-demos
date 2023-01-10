package com.guo.account.service;

import com.guo.account.mapper.AccountMapper;
import com.guo.account.model.Account;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    private static final Logger logger= LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountMapper accountMapper;

    /**
     * 查询账户是否存在, 查询余额是否充足, 以及冻结金钱
     * @param userId
     * @param money
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean prepare(String userId, Double money) {
        Account account=accountMapper.getAccountById(userId);
        if(account==null){
            throw new RuntimeException("账户不存在");
        }
        if(account.getMoney()<money){
            throw new RuntimeException("账户余额不足");
        }
        account.setFreezeMoney(account.getFreezeMoney()+money);
        account.setMoney(account.getMoney()-money);
        int i=accountMapper.updateAccount(account);
        logger.info("{} 账户预扣款 {} 元", userId, money);
        return i==1;
    }

    // 真正的扣款, 就是拿freezeMoney和商品价格进行比较防止并发线程将freezeMoney改回原来的
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        double money = ((BigDecimal) actionContext.getActionContext("money")).doubleValue();
        Account account = accountMapper.getAccountById(userId);

        if(account.getFreezeMoney()<money){
            throw new RuntimeException("账户余额不足, 扣款失败");
        }
        account.setFreezeMoney(account.getFreezeMoney()-money);
        Integer i =accountMapper.updateAccount(account);
        logger.info("{} 账户扣款 {} 元", userId, money);
        return i==1;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext actionContext) {
        String userId = (String) actionContext.getActionContext("userId");
        double money = ((BigDecimal) actionContext.getActionContext("money")).doubleValue();
        Account account = accountMapper.getAccountById(userId);
        if(account.getFreezeMoney()>=money){
            account.setFreezeMoney(account.getFreezeMoney()-money);
            account.setMoney(account.getMoney()+money);
            int i = accountMapper.updateAccount(account);
            logger.info("{} 账户释放冻结金额 {} 元", userId, money);
            return i==1;
        }
        logger.info("{} 账户未进行资金冻结就抛出了异常, 账户不存在或余额不足", userId);
        return true;
    }
}

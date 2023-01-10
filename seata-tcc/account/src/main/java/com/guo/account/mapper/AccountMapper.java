package com.guo.account.mapper;

import com.guo.account.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    @Select("select * from account_tbl where user_id=#{userId}")
    Account getAccountById(String userId);

    @Update("update account_tbl set money=#{money},freezeMoney=#{freezeMoney} where user_id=#{userId}")
    Integer updateAccount(Account account);
}

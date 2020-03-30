package com.bob.bank.client.export.account;

import com.bob.bank.client.export.account.fallback.BankAccountFeignFallback;
import com.bob.bank.client.model.BankAccount;
import com.bob.bank.client.result.PojoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wb-jjb318191
 * @create 2018-10-19 17:06
 */
@FeignClient(value = "bank-account", fallback = BankAccountFeignFallback.class)
public interface BankAccountFeignService {

    /**
     * 创建账号
     *
     * @param bankAccount
     * @return
     */
    @PostMapping("/bank/account")
    PojoResult<Boolean> createAccount(BankAccount bankAccount);

    /**
     * 查询指定用户的银行账号
     *
     * @param userId
     * @return
     */
    @GetMapping("/bank/account/{userId}")
    PojoResult<BankAccount> getBankAccountByUserId(@PathVariable("userId") Integer userId);

}
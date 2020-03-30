package com.bob.bank.account.controller;

import java.util.Collection;

import com.bob.bank.account.service.BankAccountService;
import com.bob.bank.client.model.BankAccount;
import com.bob.bank.client.result.PojoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wb-jjb318191
 * @create 2018-10-19 16:58
 */
@RestController
@RequestMapping("/bank/account")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Spring5.0之后,默认没有加载{@link Endpoint}标识的Endpoint,也就是单纯的"/refresh"不会起作用
     */
    @Autowired
    private RefreshEndpoint refreshEndpoint;

    /**
     * 创建账号
     *
     * @param bankAccount
     * @return
     */
    @PostMapping
    public PojoResult<Boolean> createAccount(@RequestBody BankAccount bankAccount) {
        return new PojoResult<>(bankAccountService.create(bankAccount) > 0);
    }

    /**
     * 查询用户指定账号
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public PojoResult<BankAccount> getAccount(@PathVariable Integer userId) {
        return new PojoResult<>(bankAccountService.getById(userId));
    }

    /**
     * 刷新环境
     *
     * @return
     */
    @PostMapping("/refresh")
    public Collection<String> refreshEnvironment() {
        return refreshEndpoint.refresh();
    }

}

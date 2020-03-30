package com.bob.bank.user.controller;

import com.bob.bank.client.model.BankAccount;
import com.bob.bank.client.result.PojoResult;
import com.bob.bank.user.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wb-jjb318191
 * @create 2018-10-19 11:58
 */
@RestController
@RequestMapping("/bank/user")
public class BankUserController {

    @Autowired
    private BankUserService bankUserService;

    /**
     * 提升用户的账号等级
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}/rank")
    public PojoResult<Boolean> upgradeAccountRank(@PathVariable Integer id) {
        return new PojoResult<>(bankUserService.upgradeAccountRank(id));
    }

    /**
     * 查询指定Id的用户是否存在
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/exists")
    public PojoResult<Boolean> checkIfUserExists(@PathVariable Integer id) {
        return new PojoResult<>(bankUserService.checkIfUserExists(id));
    }

    /**
     * 查询指定用户的银行账号
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/account")
    public PojoResult<BankAccount> getAccountByUserId(@PathVariable Integer id) {
        return new PojoResult<>(bankUserService.getBankAccount(id));
    }

}

package com.bob.bank.user.service;

import com.bob.bank.client.model.BankAccount;

/**
 * @author wb-jjb318191
 * @create 2018-10-23 9:47
 */
public interface BankUserService {

    /**
     * 提升账户等级
     *
     * @param userId
     * @return
     */
    boolean upgradeAccountRank(Integer userId);

    /**
     * 验证用户是否存在
     *
     * @param userId
     * @return
     */
    boolean checkIfUserExists(Integer userId);

    /**
     * @param userId
     * @return
     */
    BankAccount getBankAccount(Integer userId);

}
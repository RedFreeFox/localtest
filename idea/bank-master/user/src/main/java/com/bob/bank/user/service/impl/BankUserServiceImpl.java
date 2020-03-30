package com.bob.bank.user.service.impl;

import com.bob.bank.client.export.account.BankAccountFeignService;
import com.bob.bank.client.model.BankAccount;
import com.bob.bank.client.result.PojoResult;
import com.bob.bank.user.mapper.BankUserMapper;
import com.bob.bank.user.service.BankUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wb-jjb318191
 * @create 2018-10-23 9:48
 */
@Service
public class BankUserServiceImpl implements BankUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankUserServiceImpl.class);

    @Autowired
    private BankAccountFeignService bankAccountFeignService;

    @Autowired
    private BankUserMapper bankUserMapper;

    @Override
    public boolean upgradeAccountRank(Integer userId) {
        return false;
    }

    @Override
    public boolean checkIfUserExists(Integer userId) {
        return bankUserMapper.selectByPrimaryKey(userId) != null;
    }

    @Override
    public BankAccount getBankAccount(Integer userId) {
        boolean exists = bankUserMapper.selectByPrimaryKey(userId) != null;
        if (!exists) {
            LOGGER.debug("用户Id:[{}]不存在", userId);
            return null;
        }
        PojoResult<BankAccount> result = bankAccountFeignService.getBankAccountByUserId(userId);
        if (!result.isSuccess()) {
            LOGGER.debug("查询用户Id:[{}]失败,ErrorCode:[{}]", userId, result.getErrorCode());
            return null;
        }
        return result.getContent();
    }
}

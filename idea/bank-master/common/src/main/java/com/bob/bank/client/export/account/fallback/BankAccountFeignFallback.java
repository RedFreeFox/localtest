package com.bob.bank.client.export.account.fallback;

import com.bob.bank.client.export.account.BankAccountFeignService;
import com.bob.bank.client.model.BankAccount;
import com.bob.bank.client.result.PojoResult;
import com.bob.bank.client.utils.ErrorResultGenerator;
import org.springframework.stereotype.Service;

import static com.bob.bank.client.constants.ErrorCodeEnum.REST_REQUEST_ERROR;
import static com.bob.bank.client.constants.ErrorCodeEnum.SYSTEM_ERROR;

/**
 * @author wb-jjb318191
 * @create 2018-10-23 14:20
 */
@Service
public class BankAccountFeignFallback implements BankAccountFeignService {

    @Override
    public PojoResult<Boolean> createAccount(BankAccount bankAccount) {
        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, false);
    }

    @Override
    public PojoResult<BankAccount> getBankAccountByUserId(Integer userId) {
        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, null);
    }

}
